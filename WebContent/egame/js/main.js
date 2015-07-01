function Home(){
	this.selectedChapterIndex = 0
}
Home.prototype.init = function(){	
	//设置第一个缩略图背景
	$thumbs[0].style.cssText = "background: #00A99D url(img/1.png) center no-repeat;"
	$thumbs[1].style.cssText = "background: #00A99D url(img/2.png) center no-repeat;"
	$thumbs[2].style.cssText = "background: #00A99D url(img/3.png) center no-repeat;"
	//默认第一个缩略图被选中效果
	$thumbs[0].className = "thumbsClick"
	$goBtn.css({"background": "#98D644","border":"solid 1px #009144"}) 
	//设置星星显示状态
	var starScore = chapter1Star == '' ? 0 : chapter1Star
	var chapter01_star = document.getElementById("chapter01_star")
	for(var i=0;i<starScore;i++){
		chapter01_star.children[i].className = 'starFull'
	}		
	
}
Home.prototype.selectChapter = function(ev){	
	for(var i=0;i<$thumbs.length;i++){
	  //把所有的图片设置成锁
		$thumbs[i].className = "thumbs"	
		  
		if(ev.target.id == $thumbs[i].id){
			$thumbs[i].className = 'thumbsClick'
	    //goBtn的显示效果（已激活显示为绿色，未激活显示为灰色）
	    if(i <= chapterkeys){
	      $goBtn.css({"background": "#98D644","border":"solid 1px #009144"})  
	    }else{
	      $goBtn.css({"background": "lightgray","border":"solid 1px darkgrey"}) 
	    }
			$start.attr('href', ctx+"/egame/chapter.do?ownerId="+ownerId+"&playerId="+playerId+"&chapterNum=" + (i+1))
		}
	}   
}
