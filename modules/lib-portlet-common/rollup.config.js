import babel from '@rollup/plugin-babel';
import commonjs from '@rollup/plugin-commonjs';
import terser from '@rollup/plugin-terser';
import del from 'rollup-plugin-delete';
import replace from 'rollup-plugin-replace';
import typescript from 'rollup-plugin-typescript2';
import ttypescript from 'ttypescript';
import pkg from './package.json';

export default {
    input: 'src/index.ts',
    output: [
        {
            format: 'cjs',
            dir: 'dist/cjs',
            sourcemap: true
        },
        {
            format: 'es',
            dir: 'dist/esm',
            sourcemap: true
        },
    ],
    external: [
        ...Object.keys(pkg.dependencies || {}),
        ...Object.keys(pkg.devDependencies || {}),
        ...Object.keys(pkg.peerDependencies || {}),
        /node_modules/
    ],
    plugins: [
        typescript({
            typescript: ttypescript,
            tsconfig: './tsconfig.json',
        }),
        commonjs(),
        babel({
            babelHelpers: 'runtime',
            exclude: 'node_modules/**',
            extensions: ['.ts', '.tsx'],
        }),
        replace({
            ENV: JSON.stringify(process.env.NODE_ENV || 'development'),
            'process.env.NODE_ENV': JSON.stringify(process.env.NODE_ENV || 'development'),
        }),
        (process.env.NODE_ENV === 'production' && terser()),
        del({ targets: 'dist/*' }),
    ],
};
