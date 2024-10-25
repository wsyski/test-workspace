import commonjs from "@rollup/plugin-commonjs";
import terser from '@rollup/plugin-terser';
import {readFileSync} from "node:fs";
import dts from "rollup-plugin-dts";
import peerDepsExternal from 'rollup-plugin-peer-deps-external';
import typescript from "rollup-plugin-typescript2";



const packageJson = JSON.parse(readFileSync(new URL('./package.json', import.meta.url), 'utf-8'));

const getPlugins = () => {

    return [
        peerDepsExternal(),
        commonjs(),
        typescript({tsconfig: "./tsconfig.json"}),
        (process.env.NODE_ENV === 'production' && terser())
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
                sourcemap: true,
            },
            {
                file: packageJson.module,
                format: "esm",
                sourcemap: true,
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
