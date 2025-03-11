import commonjs from "@rollup/plugin-commonjs";
import dts from "rollup-plugin-dts";
import peerDepsExternal from 'rollup-plugin-peer-deps-external';
import replace from '@rollup/plugin-replace';
import resolve from "@rollup/plugin-node-resolve";
import terser from "@rollup/plugin-terser";
import typescript from "rollup-plugin-typescript2";
import {readFileSync} from "node:fs";

const packageJson = JSON.parse(readFileSync(new URL('./package.json', import.meta.url), 'utf-8'));
const isProduction = process.env.NODE_ENV === 'production';

const getPlugins = () => {

    return [
        replace({
            preventAssignment: true,
            'process.env.NODE_ENV': JSON.stringify(isProduction ? 'production' : 'development'),
        }),
        typescript({tsconfig: "./tsconfig.json",tsconfigDefaults: {inlineSources: true, sourceMap: true}}),
        peerDepsExternal(),
        commonjs(),
        resolve({resolveOnly: ['photoswipe', 'react-photoswipe-gallery']}),
        (isProduction && terser())
    ];
};


export default [
    {
        external: ['node_modules'],
        input: 'src/index.ts',
        output: [
            {
                file: packageJson.main,
                format: 'cjs',
                interop: 'auto',
                sourcemap: true,
                globals: { react: 'React' }
            },
            {
                file: packageJson.module,
                format: "esm",
                sourcemap: true,
                globals: { react: 'React' }
            }
        ],
        plugins: getPlugins()
    },
    {
        input: "dist/esm/index.d.ts",
        output: [{file: "dist/index.d.ts", format: "esm"}],
        plugins: [dts()]
    },
];
