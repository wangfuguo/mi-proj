// Place third party dependencies in the lib folder
//
// Configure loading modules from the lib directory,
// except 'app' ones,
requirejs.config({
    "baseUrl": AuthWidgetModuleSwitcherConfig.remoteBasePath,
    "paths": {
        "app": "../plugin/js"
    }
});

// Load the main app module to start the app
requirejs([
AuthWidgetModuleSwitcherConfig.remoteEntryPoint
]);
