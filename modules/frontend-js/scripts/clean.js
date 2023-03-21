const fs = require('fs-extra');
const path = require('path');

const {abort} = require('./util/report');

function hasValidOutDir(json) {
    const {compilerOptions} = json;

    if (!compilerOptions) {
        return false;
    }

    const {outDir} = compilerOptions;

    if (!outDir) {
        return false;
    }

    const absOutDir = path.resolve(outDir);
    const projectDir = path.resolve('.');

    if (path.relative(projectDir, absOutDir).startsWith('../')) {
        return false;
    }

    return true;
}

try {
    const tsconfig = (process.argv.length > 2) ? process.argv[2] : 'tsconfig.json';
    const json = fs.readJSONSync(tsconfig);

    if (hasValidOutDir(json)) {
        fs.removeSync(json.compilerOptions.outDir);
    }
} catch (err) {
    if (err.code !== 'ENOENT') {
        abort(err.toString());
    }
}

fs.removeSync('tsconfig.tsbuildinfo');
