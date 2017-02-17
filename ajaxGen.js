const readline = require('readline');
const fs = require('fs');
const path = require('path');

const fWriteName = path.join(path.dirname(process.argv[1]), '/sliceVisualization/ajax/ajax.json');

var
    dir = path.resolve(process.argv[2]),
    ajaxData = {
        nodes: [],
        edges: []
    };

var fileProcess = (fileName) => {
    let fRead = fs.createReadStream(fileName);

    fRead.on('end', ()=>{
        console.log(`${fileName} process finished!`);
    });

    let objReadline = readline.createInterface({
        input: fRead,
    });

    objReadline.on('close', ()=>{
        console.log('readline close...');
        fs.writeFile(fWriteName, JSON.stringify(ajaxData, null, ' '), (err) => {
            if(err){
                console.log(err);
            }else{
                console.log('ajaxData saved successfully!');
            }
        });
    });

    objReadline.on('line', (line) => {
        console.log(line);
        if(fileName.match('Slice.out$')){
            ajaxData.nodes.push(/(.+):(SRC|CTL|VAL|IDL)$/.exec(line)[1]);
        }else if(fileName.match('Depen.out$')){
            let fragment = line.split('@');
            ajaxData.edges.push([fragment[0], fragment[1]]);
        }else{
            let fragment = line.split('@');
            ajaxData.edges.push([fragment[0], fragment[1]]);
            ajaxData.edges.push([fragment[1], fragment[0]]);
        }
    });
}

fileProcess(path.join(dir, 'srcSlice.out'));
fileProcess(path.join(dir, 'dstSlice.out'));
fileProcess(path.join(dir, 'srcDepen.out'));
fileProcess(path.join(dir, 'dstDepen.out'));
fileProcess(path.join(dir, 'srcCorres.out'));
fileProcess(path.join(dir, 'dstCorres.out'));
