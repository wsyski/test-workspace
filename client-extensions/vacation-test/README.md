# Liferay React Custom Element Client Extension

This project was created with the help of the create-react-cx script by Dave Nebinger.

It uses Vite for the build and is configured to build a Client Extension that leverages Liferay's deployed version of React (16.12.0) and is also ready to use Clay components.

- To build the project, run: `blade gw build`
- To deploy to a locally running Liferay bundle, run: `blade gw deploy`

The generated `.zip` file can be dropped into Liferay's `osgi/client-extensions` folder.
