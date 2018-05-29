
var fs = require('fs');
var esprima = require('esprima');

var filename =process.argv[2];
var code = fs.readFileSync(filename);
var outputFilename =process.argv[3];

fs.writeFile(outputFilename, JSON.stringify(esprima.parse(code), null, 4), function(err) {
    if(err) {
      console.log(err);
    } else {
      console.log("JSON saved to " + outputFilename);
    }
}); 