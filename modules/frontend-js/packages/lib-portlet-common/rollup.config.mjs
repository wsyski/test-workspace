import babel from '@rollup/plugin-babel';
import commonjs from "@rollup/plugin-commonjs";
import typescript from "@rollup/plugin-typescript";
import terser from '@rollup/plugin-terser';
import path from 'path';

import pkg from './package.json' assert { type: 'json' };
const babelRuntimeVersion = pkg.devDependencies['@babel/runtime'].replace(/^[^0-9]*/, '');
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
const input = 'src/index.ts';
const external = [...Object.keys(pkg.dependencies || {}), ...Object.keys(pkg.peerDependencies || {})];

const getPlugins = (format = 'esm') => {
    const typescriptOptions = format === 'esm' ? { declaration: true, declarationDir: path.dirname(pkg.module) } : {declaration: true, declarationDir: path.dirname(pkg.main)};

    return [
        typescript({ tsconfig: "./tsconfig.json", ...typescriptOptions}),
        commonjs(),
        babel({
            babelHelpers: 'runtime',
            exclude: /node_modules/,
            plugins: [['@babel/plugin-transform-runtime', { version: babelRuntimeVersion }], ["@babel/plugin-proposal-nullish-coalescing-operator"], ["@babel/plugin-proposal-class-properties"]],
            presets: [
                ['@babel/preset-env', { targets: 'defaults' }],
                ['@babel/preset-react', { runtime: 'automatic' }],
            ],
        }),
        (process.env.NODE_ENV === 'production' && terser())
    ];
};

const getOutput = (format = 'esm') => {
    return {
        dir: 'dist/' + format,
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
