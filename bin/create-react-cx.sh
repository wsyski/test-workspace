#!/bin/bash
#set -v -x
set -u -e

# Function to display the welcome message
welcome() {
    echo "Welcome to the create-react-cx script!"
    echo "This script will help you create a new React app that leverages Liferay's version of React."
    echo "By the time the script is finished, you will have a basic Liferay React Custom Element Client Extension."
    echo "create-react-cx [-s scope] [-e element-name] project-name"
}

# Function to check for required commands
check_dependencies() {
    MISSING_COMMANDS=()
    for cmd in yarn jq sed gawk; do
        if ! command -v "$cmd" >/dev/null 2>&1; then
            MISSING_COMMANDS+=("$cmd")
        fi
    done

    if [ ${#MISSING_COMMANDS[@]} -ne 0 ]; then
        echo "Error: The following required commands are not installed: ${MISSING_COMMANDS[*]}"
        exit 1
    fi
}

# Function to validate a name (no spaces, not empty)
validate_name() {
    local name="$1"
    local label="$2"
    if [[ "$name" =~ \  ]]; then
        echo "Error: $label should not contain spaces."
        return 1
    elif [[ -z "$name" ]]; then
        echo "Error: $label cannot be empty."
        return 1
    else
        return 0
    fi
}

# Function to validate the project name
validate_project_name() {
    local project_path="$BASE_DIR/$PROJECT_NAME"
    if ! validate_name "$PROJECT_NAME" "Project name"; then
        return 1
    elif [ -e "$project_path" ]; then
        echo "Error: A file or directory named '$PROJECT_NAME' already exists in '$BASE_DIR'."
        return 1
    else
        return 0
    fi
}

# Function to get the project name from the user or arguments
get_project_name() {
    if [ -n "$1" ]; then
        PROJECT_NAME="$1"
        if ! validate_project_name; then
            exit 1
        fi
    else
        while true; do
            read -p "Please enter the name of your project (no spaces): " PROJECT_NAME
            if validate_project_name; then
                break
            fi
        done
    fi
}

# Function to parse command line arguments
parse_arguments() {
    SCOPE="liferay"  # Default scope
    ELEMENT_NAME=""  # Will default to PROJECT_NAME if not provided

    while getopts "s:e:" opt; do
        case $opt in
            s)
                SCOPE="$OPTARG"
                if ! validate_name "$SCOPE" "Scope"; then
                    exit 1
                fi
                ;;
            e)
                ELEMENT_NAME="$OPTARG"
                if ! validate_name "$ELEMENT_NAME" "Element name"; then
                    exit 1
                fi
                ;;
            \?)
                echo "Invalid option: -$OPTARG"
                exit 1
                ;;
        esac
    done
    shift $((OPTIND -1))
    get_project_name "$1"
    if [ -z "$ELEMENT_NAME" ]; then
        ELEMENT_NAME="$PROJECT_NAME"
    fi
}

# Function to set base directory
set_base_directory() {
    CURRENT_DIR_NAME=$(basename "$PWD")
    if [ "$CURRENT_DIR_NAME" = "client-extensions" ]; then
        BASE_DIR="$PWD"
    elif [ -d "./client-extensions" ]; then
        BASE_DIR="$PWD/client-extensions"
    else
        echo "Error: Please run this script from within the 'client-extensions' directory or a directory containing 'client-extensions'."
        exit 1
    fi
}

# Function to update package.json
update_package_json() {
    PACKAGE_JSON="$PROJECT_PATH/package.json"
    if [ -f "$PACKAGE_JSON" ]; then
        jq --arg name "@$SCOPE/$ELEMENT_NAME" '.name = $name' "$PACKAGE_JSON" > "$PACKAGE_JSON.tmp" && mv "$PACKAGE_JSON.tmp" "$PACKAGE_JSON"
        jq '.version = "1.0.0"' "$PACKAGE_JSON" > "$PACKAGE_JSON.tmp" && mv "$PACKAGE_JSON.tmp" "$PACKAGE_JSON"
        jq '.dependencies.react = "16.12.0" | .dependencies["react-dom"] = "16.12.0"' "$PACKAGE_JSON" > "$PACKAGE_JSON.tmp" && mv "$PACKAGE_JSON.tmp" "$PACKAGE_JSON"
    else
        echo "Error: package.json not found in $PROJECT_PATH"
        exit 1
    fi
}

# Function to update index.html
update_index_html() {
    INDEX_HTML="$PROJECT_PATH/index.html"
    if [ -f "$INDEX_HTML" ]; then
        sed -i "s|<div id=\"root\"></div>|<$ELEMENT_NAME></$ELEMENT_NAME>|g" "$INDEX_HTML"
        sed -i "/<head>/a\\
<link href=\"https://cdn.jsdelivr.net/npm/@clayui/css/lib/css/atlas.css\" rel=\"stylesheet\" />" "$INDEX_HTML"
    else
        echo "Error: index.html not found in $PROJECT_PATH"
        exit 1
    fi
}

# Function to overwrite src/main.jsx
overwrite_main_jsx() {
    MAIN_JSX="$PROJECT_PATH/src/main.jsx"
    if [ -f "$MAIN_JSX" ]; then
        cat > "$MAIN_JSX" <<'EOF'
import { render, unmountComponentAtNode } from 'react-dom';
import React from 'react';
import App from './App';

class WebComponent extends HTMLElement {
  connectedCallback() {
    render(
      <React.StrictMode>
        <App />
      </React.StrictMode>,
      this
    );
  }

  disconnectedCallback() {
    unmountComponentAtNode(this);
  }
}

const ELEMENT_NAME = 'element-name';

if (customElements.get(ELEMENT_NAME)) {
  // eslint-disable-next-line no-console
  console.log(`Skipping registration for <${ELEMENT_NAME}> (already registered)`);
} else {
  customElements.define(ELEMENT_NAME, WebComponent);
}
EOF
        sed -i "s/element-name/$ELEMENT_NAME/g" "$MAIN_JSX"
    else
        echo "Error: src/main.jsx not found in $PROJECT_PATH"
        exit 1
    fi
}

# Function to create client-extension.yaml
create_client_extension_yaml() {
    CLIENT_EXTENSION_YAML="$PROJECT_PATH/client-extension.yaml"
    ELEMENT_NAME_CAPITALIZED=$(echo "$ELEMENT_NAME" | sed 's/-/ /g' | gawk '{for(i=1;i<=NF;i++){$i=toupper(substr($i,1,1)) substr($i,2)}}1')
    cat > "$CLIENT_EXTENSION_YAML" <<EOF
assemble:
    - from: vite-build
      into: static
$ELEMENT_NAME:
    friendlyURLMapping: $ELEMENT_NAME
    htmlElementName: $ELEMENT_NAME
    instanceable: false
    name: $ELEMENT_NAME_CAPITALIZED
    portletCategoryName: category.client-extensions
    type: customElement
    urls:
        - assets/*.js
    useESM: true
EOF
}

# Function to overwrite vite.config.js
overwrite_vite_config_js() {
    VITE_CONFIG_JS="$PROJECT_PATH/vite.config.js"
    cat > "$VITE_CONFIG_JS" <<'EOF'
import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
  base: '/o/element-name',
  build: {
    outDir: './vite-build',
    rollupOptions: {
      external: [
        'react',
        'react-dom',
        /^(?!@clayui\/css)@clayui.*$/,
      ],
    }
  },
  plugins: [
    react({
      jsxRuntime: 'classic',
    }),
  ]
})
EOF
    sed -i "s|element-name|$ELEMENT_NAME|g" "$VITE_CONFIG_JS"
}

# Function to update README.md
update_readme_md() {
    README_MD="$PROJECT_PATH/README.md"
    cat > "$README_MD" <<EOF
# Liferay React Custom Element Client Extension

This project was created with the help of the create-react-cx script by Dave Nebinger.

It uses Vite for the build and is configured to build a Client Extension that leverages Liferay's deployed version of React (16.12.0) and is also ready to use Clay components.

- To build the project, run: \`blade gw build\`
- To deploy to a locally running Liferay bundle, run: \`blade gw deploy\`

The generated \`.zip\` file can be dropped into Liferay's \`osgi/client-extensions\` folder.
EOF
}

# Main script execution
main() {
    welcome
    check_dependencies
    set_base_directory
    parse_arguments "$@"
    echo "Project name set to: $PROJECT_NAME"
    echo "Scope set to: $SCOPE"
    echo "Element name set to: $ELEMENT_NAME"
    PROJECT_PATH="$BASE_DIR/$PROJECT_NAME"
    yarn create vite "$PROJECT_NAME" --template react
    update_package_json
    update_index_html
    overwrite_main_jsx
    create_client_extension_yaml
    overwrite_vite_config_js
    update_readme_md
    echo "Your new React Custom Element Client Extension is ready for further development!"
}

# Run the main function with all script arguments
main "$@"
