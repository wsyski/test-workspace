import babel from '@rollup/plugin-babel';
import commonjs from "@rollup/plugin-commonjs";
import typescript from "@rollup/plugin-typescript";
import terser from '@rollup/plugin-terser';

import pkg from './package.json' assert { type: 'json' };
const babelRuntimeVersion = pkg.devDependencies['@babel/runtime'].replace(/^[^0-9]*/, '');

export default [
    {
        input: "src/index.ts",
        output: [
            {
                file: pkg.main,
                format: "cjs",
                sourcemap: true,
            },
            {
                file: pkg.module,
                format: "esm",
                sourcemap: true,
            },
        ],
        external: [
            ...Object.keys(pkg.dependencies || {}),
            ...Object.keys(pkg.devDependencies || {}),
            ...Object.keys(pkg.peerDependencies || {})
        ],
        plugins: [
            typescript({ tsconfig: "./tsconfig.json" }),
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
        ],
    }
];
