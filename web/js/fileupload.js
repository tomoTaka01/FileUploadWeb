'use strict';
(function(){
    // ファイルアップ(json)
    var files = [];
    // アップ対象のファイルを取得
    var fileJson = document.getElementById('fileJson');
    fileJson.addEventListener('change', function(){
        files = this.files;
    });
    // uploadボタンをクリック時
    var upbtn = document.getElementById('uploadBtn');
    upbtn.addEventListener('click', function(){
        if (files.length === 0) {
            var msg = document.getElementById('uploadMsg');
            msg.innerHTML = 'ファイルを選択してください';
            msg.classList.add('label-warning');
            return;
        }
        // 選択したファイルを読み込み
        var file = files[0];
        var reader = new FileReader();
        var p = new Promise(function(resolve, reject){
            // 1秒以上かかった場合、エラーとする
            window.setTimeout(function(){
                reject('timeout');
            }, 1000);
            reader.onload = (function(){
                return function(e){
                // 「data:text/plain;base64,xxxxx」がresult、なのでカンマで区切って内容を取得
                var fileBase64 = e.target.result.split(',')[1];
                resolve(fileBase64);
            };
            })();
        });
        // 選択したファイルを読込む
        reader.readAsDataURL(file);
        // サーバに非同期通信
        var XHR = new XMLHttpRequest();
        XHR.open('POST', '/FileUploadWeb/webresources/upload');
        XHR.setRequestHeader('Content-Type', 'application/json; charset="UTF-8"');
        XHR.addEventListener('load', function(e){
            var msg = document.getElementById('uploadMsg');
            msg.innerHTML =  XHR.responseText;
            msg.classList.add('label-success');
        });
        p.then(
            function(fileBase64){
                var destination = document.getElementById('destinationJson').value;
                var data = {
                    'destination' : destination,
                    'fileName' : file.name,
                    'fileType' : file.type,
                    'file' : fileBase64
                };
                // ファイル読み込みが終了してから、通信開始
                XHR.send(JSON.stringify(data));
            },
            function(errMsg){
                var msg = document.getElementById('uploadMsg');
                msg.innerHTML = errMsg;
                msg.classList.add('label-warning');
        });
    });
}());
