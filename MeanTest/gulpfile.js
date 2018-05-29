System.register(["gulp", "./tools/utils"], function (exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var gulp, utils_1;
    return {
        setters: [
            function (gulp_1) {
                gulp = gulp_1;
            },
            function (utils_1_1) {
                utils_1 = utils_1_1;
            }
        ],
        execute: function () {
            gulp.task('clean', utils_1.task('clean', 'all'));
            gulp.task('clean.dist', utils_1.task('clean', 'dist'));
            gulp.task('clean.test', utils_1.task('clean', 'test'));
            gulp.task('clean.tmp', utils_1.task('clean', 'tmp'));
            gulp.task('check.versions', utils_1.task('check.versions'));
            gulp.task('postinstall', function (done) {
                return utils_1.runSequence('clean', 'npm', done);
            });
            gulp.task('build.dev', function (done) {
                return utils_1.runSequence('clean.dist', 'tslint', 'build.sass.dev', 'build.img.dev', 'build.js.dev', 'build.index', done);
            });
            gulp.task('build.prod', function (done) {
                return utils_1.runSequence('clean.dist', 'clean.tmp', 'tslint', 'build.sass.dev', 'build.img.dev', 'build.html_css.prod', 'build.deps', 'build.js.prod', 'build.bundles', 'build.index', done);
            });
            gulp.task('build.dev.watch', function (done) {
                return utils_1.runSequence('build.dev', 'watch.dev', done);
            });
            gulp.task('build.test.watch', function (done) {
                return utils_1.runSequence('build.test', 'watch.test', done);
            });
            gulp.task('test', function (done) {
                return utils_1.runSequence('clean.test', 'tslint', 'build.test', 'karma.start', done);
            });
            gulp.task('serve', function (done) {
                return utils_1.runSequence('build.dev', 'server.start', 'watch.serve', done);
            });
        }
    };
});
