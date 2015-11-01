(function(){
    console.log('ok');
    var textFileupEle = document.getElementById('textFileup');
    textFileupEle.addEventListener('click', function(event){
//        console.log('click');
//        // get context path
//        var XHR = new XMLHttpRequest();
//        var x = window.location;
//        console.log(x.pathname);
//        XHR.open('POST', '/FileUploadWeb');
//        XHR.send(null);
//        XHR.addEventListener('load', function(){
//            console.log('load');
//        });
//        XHR.addEventListener('ERROR', function(e){
//            console.log('err' + e);
//        });
        // form submit
        var myForm = document.createElement('form');
        myForm.action = "/FileUploadWeb/fileupload";
        myForm.method = "POST";
        myForm.submit();
    });

})
();

