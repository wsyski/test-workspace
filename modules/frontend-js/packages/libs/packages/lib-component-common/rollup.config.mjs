import commonjs from "@rollup/plugin-commonjs";
import terser from '@rollup/plugin-terser';
import {readFileSync} from "node:fs";
import dts from "rollup-plugin-dts";
import peerDepsExternal from 'rollup-plugin-peer-deps-external';
import typescript from "rollup-plugin-typescript2";

const packageJson = JSON.parse(readFileSync(new URL('./package.json', import.meta.url), 'utf-8'));
const input = 'src/index.ts';
const external = ['node_modules', ...Object.keys(packageJson.dependencies || {}), ...Object.keys(packageJson.peerDependencies || {})];

const getPlugins = () => {

    return [
        peerDepsExternal(),
        commonjs(),
        typescript({ useTsconfigDeclarationDir: true }),
        (process.env.NODE_ENV === 'production' && terser())
    ];
};


export default [
    {
        external,
        input,
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
        input: "dist/esm/types/index.d.ts",
        output: [{file: "dist/index.d.ts", format: "esm"}],
        plugins: [dts()]
    },
];
