// 封装getStyle函数
function getStyle(obj,attr){
    return obj.currentStyle ? obj.currentStyle[attr] : getComputedStyle(obj,false)[attr];
}

window.onload = function(){
    var oBox = document.getElementById('box');
    // alert(getStyle(oBox,'width'));        // 700px
    var oLeft = document.getElementById('left');
    var oRight = document.getElementById('right');
    var oWrap = document.getElementById('wrap');
    var num = 0;
    var timer = null;

    oLeft.onmousedown = function(){
        oWrap.timer = setInterval(function(){
            var dis = parseInt(getStyle(oWrap,'left')) - 5;
            if(dis < -650){
                dis = -650;
            }
            oWrap.style.left = dis + 'px';
        },30);
    };

    oLeft.onmouseup = function(){
        clearInterval(oWrap.timer);
    };

    oRight.onmousedown = function(){
        oWrap.timer = setInterval(function(){
            var dis = parseInt(getStyle(oWrap,'left')) + 5;
            if(dis > 0){
                dis = 0;
            }
            oWrap.style.left = dis + 'px';
        },30);
    };

    oRight.onmouseup = function(){
        clearInterval(oWrap.timer);
    };
};