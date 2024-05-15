import globals from "globals";
import pluginJs from "@eslint/js";
import pluginVue from "eslint-plugin-vue";
import vueParser from "vue-eslint-parser";
import tsParser from "@typescript-eslint/parser";
import tslint from "typescript-eslint";
import eslintConfigPrettier from "eslint-config-prettier";

export default [
  {
    languageOptions: {
      globals: globals.browser,
      parser: vueParser,
      parserOptions: {
        parser: tsParser,
        requireConfigFile: false,
      },
    },
    plugins: {},
    rules: {},
  },

  pluginJs.configs.recommended,
  ...tslint.configs.recommended,
  ...pluginVue.configs["flat/recommended"],
  ...tslint.configs.recommended,
  eslintConfigPrettier,
];
