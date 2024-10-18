'use strict';

const axios = require('axios');
const cors = require('cors');
const express = require('express');
const fs = require('fs');
const { v4: uuidv4 } = require('uuid');
const elasticsearchProtocol = 'http';
const elasticsearchHost = 'localhost';
const elasticsearchPort = 9200;

const port = process.argv[3] || 9599;
const contextPath = '/api/federated-search/latest/latest/customers';
const sourceIds = ['events1', 'k10'];

const getSourceType = (sourceId) => {
    switch (sourceId) {
        case 'events1':
            return 'EVENT';
        case 'k10':
            return 'K10';
        default:
            console.error('Unknown source id: ' + sourceId);
    }
};

const getHintsRequest = (q, size) => {
    return {
        _source: false,
        // eslint-disable-next-line @typescript-eslint/camelcase
        docvalue_fields: [
            {
                field: 'freetext.keyword',
            },
        ],
        size,
        query: {
            // eslint-disable-next-line @typescript-eslint/camelcase
            multi_match: {
                query: q,
                type: 'bool_prefix',
                fields: ['freetext.shingle', 'freetext.shingle._2gram', 'freetext.shingle._3gram'],
            },
        },
    };
};

const getHintsResponse = async (sourceId, q, size) => {
    const sourceType = getSourceType(sourceId);
    const hintsRequest = getHintsRequest(q, size);
    const hintsHeaders = {
        headers: {
            'Content-Type': 'application/json',
        },
    };
    const url = `${elasticsearchProtocol}://${elasticsearchHost}:${elasticsearchPort}/${sourceType.toLowerCase()}/_search`;
    try {
        const axiosResponse = await axios.post(url, hintsRequest, hintsHeaders);
        console.log(axiosResponse);

        return axiosResponse.data;
    } catch (error) {
        console.error(error);
    }
};

const getHints = (hintsResponse, q) => {
    let hints = [];
    if (q && q.length > 0) {
        const qLowercase = q.toLocaleLowerCase();
        const hits = hintsResponse.hits.hits;
        hits.forEach((hit) => {
            const keywords = hit.fields['freetext.keyword'];
            if (keywords) {
                hints = hints.concat(keywords);
            }
        });
        hints = hints.filter((hint) => {
            const hintLowercase = hint.toLocaleLowerCase();

            return hintLowercase.includes(qLowercase);
        });
        hints = [...new Set(hints)];

        return hints;
    }
};

const run = async () => {
    const app = express().use(cors());

    app.get(contextPath + '/:customerId/hints', async (req, res) => {
        const customerId = req.params.customerId;
        const q = req.query.queryString || '';
        const size = req.query.size || 10;

        console.log('customer id: ' + customerId + ' q: ' + q + ' size: ' + size);
        res.set({
            'Cache-Control': 'no-cache',
            'Content-Type': 'text/event-stream',
            "Connection": 'keep-alive',
        });
        res.flushHeaders();

        const promises = [];

        sourceIds.forEach((sourceId) => {
            const sourceType = getSourceType(sourceId);
            const promise = new Promise((resolve, reject) => {
                getHintsResponse(sourceId, q, size)
                    .then((hintsResponse) => {
                        const uuid = uuidv4();
                        res.write(`id:${uuid}` + '\n');
                        res.write('event:hints\n');
                        const data = { sourceId, sourceType, values: getHints(hintsResponse, q) };
                        res.write('data:' + JSON.stringify(data) + '\n\n');
                        resolve(hintsResponse);
                    })
                    .catch((error) => {
                        reject(error);
                    });
            });
            promises.push(promise);
        });

        await Promise.all(promises);
        const uuid = uuidv4();
        res.write(`id:${uuid}` + '\n');
        res.write('event:complete\n');
        const data = { name: 'COMPLETED_STREAM' };
        res.write('data:' + JSON.stringify(data) + '\n\n');
        res.end();
    });

    const index = fs.readFileSync('./server/index.html', 'utf8');
    app.get('/', (req, res) => res.send(index));

    await app.listen(port, '0.0.0.0');
    console.log('Listening on port: ' + port);
};

run().catch((error) => console.error(error));
