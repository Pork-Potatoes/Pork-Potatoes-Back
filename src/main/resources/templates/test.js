var sock = null;

$(document).ready(function(){
    connectWs();
});

function connectWs(){

    sock = new SockJS(getContextPath()+'/alarm');

    sock.onopen = function() {
        console.log('open');

    };

    sock.onmessage = function(e) {
        console.log('message', e.data);
        //  	  sock.close();
    };

    sock.onclose = function() {
        console.log('close');
    };

};

function getContextPath() {
    var hostIndex = location.href.indexOf( location.host ) + location.host.length;
    return location.href.substring( hostIndex, location.href.indexOf('/', hostIndex + 1) );
};

function likeupdate(){
    var root = getContextPath(),
        likeurl = "/like/likeupdate",
        mid = $('#mid').val(),
        bid = $('#bid').val(),
        receiver = $('#bwriter').val(),
        btitle = $('#btitle').val(),
        count = $('#likecheck').val(),
        data = {"ltmid" : mid,
            "ltbid" : bid,
            "count" : count};

    $.ajax({
        url : root + likeurl,
        type : 'PUT',
        contentType: 'application/json',
        data : JSON.stringify(data),
        success : function(result){
            console.log("수정" + result.like);
            if(count == 1){
                console.log("좋아요 취소");
                $('#likecheck').val(0);
                $('#likebtn').attr('class','btn btn-light');
                $('#likecount').html(result.like);
                if(sock){
                    var Msg = bid+","+receiver+","+count+","+btitle;
                    console.log(Msg);
                    sock.send(Msg);
                }
            }else if(count == 0){
                console.log("좋아요!");
                $('#likecheck').val(1);
                $('#likebtn').attr('class','btn btn-danger');
                $('#likecount').html(result.like);
                if(sock){
                    var Msg = bid+","+receiver+","+count+","+btitle;
                    console.log(Msg);
                    sock.send(Msg);
                }
            }
        }, error : function(result){
            console.log("에러" + result.result)
        }

    });
};