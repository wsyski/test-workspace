import babel from '@rollup/plugin-babel';
import commonjs from "@rollup/plugin-commonjs";
import resolve from "@rollup/plugin-node-resolve";
import terser from '@rollup/plugin-terser';
import path from 'path';

import pkg from './package.json' assert { type: 'json' };
const outputOptions = {
    interop: 'auto',
    sourcemap: true,
    banner: `/*
 * Arena portlet common library
 * {@link https://github.com/wsyski/test-workspace}
 * @copyright Wojciech Syski (@wsyski)
 * @license MIT
 */`,
};
const input = 'out-tsc/index.js';
const external = [...Object.keys(pkg.dependencies || {}), ...Object.keys(pkg.peerDependencies || {})];

const getPlugins = (format = 'esm') => {

    return [
        commonjs(),
        babel({
            babelHelpers: 'runtime',
            exclude: /node_modules/,
            plugins: [['@babel/plugin-transform-runtime'], ["@babel/plugin-proposal-nullish-coalescing-operator"], ["@babel/plugin-proposal-class-properties"]],
            presets: [
                ['@babel/preset-env'],
                ['@babel/preset-react'],
            ],
        }),
        (process.env.NODE_ENV === 'production' && terser())
    ];
};

const getOutput = (format = 'esm') => {
    return {
        dir: 'dist/'+format,
        format,
        ...outputOptions
    };
}

export default [
    // cjs configuration
    {
        input,
        output: getOutput('cjs'),
        plugins: getPlugins('cjs'),
        external,
    },
    // esm configuration
    {
        input,
        output: getOutput('esm'),
        plugins: getPlugins('esm'),
        external,
    }
];
