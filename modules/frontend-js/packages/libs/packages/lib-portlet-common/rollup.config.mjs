import babel from '@rollup/plugin-babel';
import commonjs from "@rollup/plugin-commonjs";
import terser from '@rollup/plugin-terser';
import {readFileSync} from "node:fs";
import peerDepsExternal from 'rollup-plugin-peer-deps-external';

const packageJson = JSON.parse(readFileSync(new URL('./package.json', import.meta.url), 'utf-8'));
const outputOptions = {
    interop: 'auto',
    sourcemap: true
};
const input = 'out-tsc/index.js';
const external = ['node_modules', ...Object.keys(packageJson.dependencies || {}), ...Object.keys(packageJson.peerDependencies || {})];

const getPlugins = () => {

    return [
        peerDepsExternal(),
        commonjs(),
        babel(),
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
    {
        external,
        input,
        output: getOutput('cjs'),
        plugins: getPlugins(),
    },
    {
        external,
        input,
        output: getOutput('esm'),
        plugins: getPlugins(),
    }
];
