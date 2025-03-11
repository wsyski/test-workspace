const childProcess = require('child_process');
const cpr = require('cpr');
const fs = require('fs');
const process = require('node:process');
const root = require('package.root');
const path = require('path');
const propertiesReader = require('properties-reader');

class BuildUtils {
    modulePath = null;
    moduleName = null;
    mavenArtifactName = null;
    mavenGroup = null;
    mavenVersion = null;
    mavenPackaging = 'jar';
    projectVersion = null;
    liferayHomeDir = null;
    liferayHost = null;
    liferayPort = null;
    liferayTomcatDir = null;
    repositoryUser = null;
    repositoryPassword = null;
    repositoryHost = null;
    repositorySnapshotRepoId = null;
    repositoryReleaseRepoId = null;
    repositoryPath = null;
    repositoryProtocol = null;

    constructor(options) {
        this.modulePath = options.modulePath;
        const rootPath = path.resolve(__dirname, root.deployPath, root.directory);
        const gradlePropertiesPath = path.resolve(__dirname, rootPath, 'gradle.properties');
        const gradleProperties = propertiesReader(gradlePropertiesPath);
        const pkgProperties = JSON.parse(fs.readFileSync(this.modulePath + '/package.json', 'utf-8'));
        this.moduleName = pkgProperties.name;
        this.mavenArtifactName = pkgProperties.name.replace(/@.*\//, '');
        this.mavenGroup = gradleProperties.get('mavenGroup');
        this.mavenVersion = gradleProperties.get('mavenVersion');
        this.mavenPackaging = pkgProperties.mavenPackaging || 'jar';
        this.projectVersion = pkgProperties.version;
        this.liferayHomeDir = gradleProperties.get('liferay.workspace.home.dir');
        this.liferayHost = gradleProperties.get('liferayHost');
        this.liferayPort = gradleProperties.get('liferayPort');
        this.liferayTomcatDir = gradleProperties.get('liferayTomcatDir');
        this.repositoryUser = gradleProperties.get('repositoryUser') || process.env.NEXUS_USER;
        this.repositoryPassword = gradleProperties.get('repositoryPassword') || process.env.NEXUS_PASSWORD;
        this.repositoryPath = gradleProperties.get('repositoryPath');
        this.repositoryHost = gradleProperties.get('repositoryHost');
        this.repositorySnapshotRepoId = gradleProperties.get('repositorySnapshotRepoId');
        this.repositoryReleaseRepoId = gradleProperties.get('repositoryReleaseRepoId');
        this.repositoryProtocol = gradleProperties.get('repositoryProtocol');
    }

    isRelease() {
        if (/^[0-9]+.[0-9]+.[0-9]+$/.test(this.mavenVersion)) {
            return true;
        }

        return false;
    }

    isSnapshot() {
        if (/^[0-9]+.[0-9]+.[0-9]+-SNAPSHOT$/.test(this.mavenVersion)) {
            return true;
        }

        return false;
    }

    getMavenRepository() {
        const baseUrl =
            this.repositoryProtocol +
            '://' +
            this.repositoryUser +
            ':' +
            this.repositoryPassword +
            '@' +
            this.repositoryHost +
            this.repositoryPath +
            '/';

        if (this.isSnapshot()) {
            return {
                id: this.repositorySnapshotRepoId,
                url: baseUrl + this.repositorySnapshotRepoId,
            };
        } else if (this.isRelease()) {
            return {
                id: this.repositoryReleaseRepoId,
                url: baseUrl + this.repositoryReleaseRepoId,
            };
        }
    }

    deploy() {

        const liferayDeployDir = path.join(this.liferayHomeDir, "deploy");
        cpr(
            "dist",
            liferayDeployDir,
            {
                confirm: false,
                filter: (path) => /.*(\.jar|\.war)$/.test(path),
                overwrite: true
            },
            (error) => {
                if (error) {
                    console.error(error);
                    process.exit(1);
                }
            }
        );
    }

    publish(isVersion) {
        const mavenRepository = this.getMavenRepository();
        const cmdDeploy =
            'mvn -B deploy:deploy-file -Dpackaging=' +
            this.mavenPackaging +
            ' -Dfile=dist/' +
            this.mavenArtifactName + (isVersion ? '-' + this.projectVersion : '') +
            '.' +
            this.mavenPackaging +
            ' -DgroupId=' +
            this.mavenGroup +
            ' -DartifactId=' +
            this.mavenArtifactName +
            ' -Dversion=' +
            this.mavenVersion +
            ' -DgeneratePom=true' +
            ' -DrepositoryId=' +
            mavenRepository.id +
            ' -Durl=' +
            mavenRepository.url;
        childProcess.execSync(cmdDeploy, {stdio: 'inherit'});
        const cmdInstall =
            'mvn -B install:install-file -Dpackaging=' +
            this.mavenPackaging +
            ' -Dfile=dist/' +
            this.mavenArtifactName + (isVersion ? '-' + this.projectVersion : '') +
            '.' +
            this.mavenPackaging +
            ' -DgroupId=' +
            this.mavenGroup +
            ' -DartifactId=' +
            this.mavenArtifactName +
            ' -Dversion=' +
            this.mavenVersion +
            ' -DgeneratePom=true';
        childProcess.execSync(cmdInstall, {stdio: 'inherit'});
    }

    liferayTheme() {
        const fileName = process.cwd() + '/liferay-theme.json';
        if (!fs.existsSync(fileName)) {
            const o = {
                LiferayTheme: {
                    appServerPath: this.liferayHomeDir + this.liferayTomcatDir,
                    deployPath: this.liferayHomeDir + '/deploy',
                    deployed: false,
                    deploymentStrategy: 'LocalAppServer',
                    pluginName: this.moduleName,
                    url: 'http://' + this.liferayHost + ':' + this.liferayPort,
                },
            };
            fs.writeFileSync(fileName, JSON.stringify(o));
        }
    }

    liferayPlugin() {
        const fileName = process.cwd() + '/liferay-plugin.json';
        if (!fs.existsSync(fileName)) {
            const o = {
                LiferayPlugin: {
                    appServerPath: this.liferayHomeDir + this.liferayTomcatDir,
                    appServerPathPlugin:
                        this.liferayHomeDir +
                        this.liferayTomcatDir +
                        '/webapps/' +
                        this.moduleName,
                    deployPath: this.liferayHomeDir + '/deploy',
                    deployed: false,
                    deploymentStrategy: 'LocalAppServer',
                    pluginName: this.moduleName,
                    url: 'http://' + this.liferayHost + ':' + this.liferayPort,
                },
            };
            fs.writeFileSync(fileName, JSON.stringify(o));
        }
    }

    clean(portletDir) {
        const shouldBeDeleted = ['build', 'dist', 'out-tsc'];
        const files = fs.readdir(portletDir);
        files.forEach((file) => {
            if (shouldBeDeleted.includes(file)) {
                fs.rmdir(path.join(portletDir, file), {recursive: true});
                console.log(file + ' was deleted');
            }
        });
    }
}

module.exports = BuildUtils;
