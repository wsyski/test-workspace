import babel from '@rollup/plugin-babel';
import commonjs from "@rollup/plugin-commonjs";
import resolve from "@rollup/plugin-node-resolve";
import typescript from "@rollup/plugin-typescript";
import peerDepsExternal from 'rollup-plugin-peer-deps-external';
import terser from '@rollup/plugin-terser';
import analyze from 'rollup-plugin-analyzer';

import pkg from './package.json' assert { type: 'json' };

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
            peerDepsExternal(),
            // resolve(),
            commonjs(),
            babel({
                babelHelpers: 'runtime',
                exclude: 'node_modules/**',
                extensions: ['.ts', '.tsx'],
            }),
            (process.env.NODE_ENV === 'production' && terser()),
            analyze({
                hideDeps: true,
                limit: 0,
                summaryOnly: true,
            })
        ],
    }
];
