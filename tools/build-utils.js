'use strict';

module.exports = (function () {
	const cpr = require('cpr');
	const fs = require('fs');
	const {promises} = require('fs');
	const childProcess = require('child_process');
	const path = require('path');
	const propertiesReader = require('properties-reader');
	const gradleProperties = propertiesReader(
		path.resolve(__dirname, '../', 'gradle.properties')
	);
	const pkgProperties = require(process.cwd() + '/package.json');
	const mavenArtifactName = pkgProperties.name.replace(/@.*\//, '');
	const mavenGroup = gradleProperties.get('mavenGroup');
	const mavenVersion = gradleProperties.get('mavenVersion');
	const mavenPackaging = pkgProperties.mavenPackaging || 'jar';
	const liferayHomeDir = gradleProperties.get('liferay.workspace.home.dir');
	const liferayHost = gradleProperties.get('liferayHost');
	const liferayPort = gradleProperties.get('liferayPort');
	const liferayTomcatDir = gradleProperties.get('liferayTomcatDir');

	function isRelease() {
		if (/^[0-9]+.[0-9]+.[0-9]+$/.test(mavenVersion)) {
			return true;
		}

		return false;
	}

	function isSnapshot() {
		if (/^[0-9]+.[0-9]+.[0-9]+-SNAPSHOT$/.test(mavenVersion)) {
			return true;
		}

		return false;
	}

	function getMavenRepository() {
		const user =
			gradleProperties.get('repositoryUser') || process.env.NEXUS_USER;
		const password =
			gradleProperties.get('repositoryPassword') ||
			process.env.NEXUS_PASSWORD;
		const host = gradleProperties.get('repositoryHost');
		const snapshotRepoId = gradleProperties.get('repositorySnapshotRepoId');
		const releaseRepoId = gradleProperties.get('repositoryReleaseRepoId');
		const repositoryProtocol = gradleProperties.get('repositoryProtocol');
		const baseUrl =
			repositoryProtocol +
			'://' +
			user +
			':' +
			password +
			'@' +
			host +
			gradleProperties.get('repositoryPath') +
			'/';

		if (isSnapshot()) {
			return {
				id: snapshotRepoId,
				url: baseUrl + snapshotRepoId,
			};
		} else if (isRelease()) {
			return {
				id: releaseRepoId,
				url: baseUrl + releaseRepoId,
			};
		}
	}

  function deploy() {
    const liferayHomeDir = gradleProperties.get(
      "liferay.workspace.home.dir"
    );
    const liferayDeployDir = path.join(liferayHomeDir, "deploy");
    cpr(
      "dist",
      liferayDeployDir,
      {
        confirm: false,
        filter: (path) => /.*(\.jar|\.war)$/.test(path),
        overwrite: true
      },
      (err, files) => {
        if (err) {
          console.error(err);
          process.exit(1);
        }
      }
    );
  }

	function publish() {
		var mavenRepository = getMavenRepository();
		var cmdDeploy =
			'mvn -B deploy:deploy-file -Dpackaging=' +
			mavenPackaging +
			' -Dfile=dist/' +
			mavenArtifactName +
			'.' +
			mavenPackaging +
			' -DgroupId=' +
			mavenGroup +
			' -DartifactId=' +
			mavenArtifactName +
			' -Dversion=' +
			mavenVersion +
			' -DgeneratePom=true' +
			' -DrepositoryId=' +
			mavenRepository.id +
			' -Durl=' +
			mavenRepository.url;
		childProcess.execSync(cmdDeploy, {stdio: 'inherit', stderr: 'inherit'});
		var cmdInstall =
			'mvn -B install:install-file -Dpackaging=' +
			mavenPackaging +
			' -Dfile=dist/' +
			mavenArtifactName +
			'.' +
			mavenPackaging +
			' -DgroupId=' +
			mavenGroup +
			' -DartifactId=' +
			mavenArtifactName +
			' -Dversion=' +
			mavenVersion +
			' -DgeneratePom=true';
		childProcess.execSync(cmdInstall, {
			stdio: 'inherit',
			stderr: 'inherit',
		});
	}

	function liferayTheme() {
		var fileName = process.cwd() + '/liferay-theme.json';
		if (!fs.existsSync(fileName)) {
			var o = {
				LiferayTheme: {
					deploymentStrategy: 'LocalAppServer',
					appServerPath: liferayHomeDir + liferayTomcatDir,
					deployPath: liferayHomeDir + '/deploy',
					url: 'http://' + liferayHost + ':' + liferayPort,
					deployed: false,
					pluginName: pkgProperties.name,
				},
			};
			fs.writeFileSync(fileName, JSON.stringify(o));
		}
	}

	function liferayPlugin() {
		var fileName = process.cwd() + '/liferay-plugin.json';
		if (!fs.existsSync(fileName)) {
			var o = {
				LiferayPlugin: {
					deploymentStrategy: 'LocalAppServer',
					appServerPath: liferayHomeDir + liferayTomcatDir,
					deployPath: liferayHomeDir + '/deploy',
					url: 'http://' + liferayHost + ':' + liferayPort,
					appServerPathPlugin:
						liferayHomeDir +
						liferayTomcatDir +
						'/webapps/' +
						pkgProperties.name,
					deployed: false,
					pluginName: pkgProperties.name,
				},
			};
			fs.writeFileSync(fileName, JSON.stringify(o));
		}
	}

	async function clean(portletDir) {
		const shouldBeDeleted = ['build', 'dist', 'out-tsc'];
		const files = await promises.readdir(portletDir);
		files.forEach((file) => {
			if (shouldBeDeleted.includes(file)) {
				promises.rmdir(path.join(portletDir, file), {recursive: true});
				console.log(file + ' was deleted');
			}
		});
	}

	return {
		deploy,
		liferayPlugin,
		liferayTheme,
		publish,
		clean,
	};
})();
