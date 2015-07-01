function Home(){
	this.selectedChapterIndex = 0
}
Home.prototype.init = function(){	
	//默认第一个缩略图被选中效果
	$thumbs[0].className = "thumbsClick"
	$goBtn.css({"background": "#98D644","border":"solid 1px #009144"}) 
	//设置星星显示状态
	for(var i=0;i<chapter1Heart;i++){
	  chapter01_heart.children[i].className = 'heartFull'
	}		

  for(var i=0;i<chapter2Heart;i++){
    chapter02_heart.children[i].className = 'heartFull'
  } 

  for(var i=0;i<chapter3Heart;i++){
    chapter03_heart.children[i].className = 'heartFull'
  } 
  
  for(var i=0;i<chapter4Heart;i++){
    chapter04_heart.children[i].className = 'heartFull'
  } 
  //设置关卡图片
  
  for(var i = 0;i<chapterkeys+1;i++){
    if (i < 3) {
      $thumbs[i].style.cssText = "background: #00A99D url(img/"+(i+1)+".png) center no-repeat;" 
    }
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
	      $start.attr('href', ctx+"/egame/chapter.do?ownerId="+ownerId+"&playerId="+playerId+"&chapterNum=" + (i+1))
	      $goBtn.css({"background": "#98D644","border":"solid 1px #009144"})  
	    }else{
	      $start.attr('href', 'javascript:')
	      $goBtn.css({"background": "lightgray","border":"solid 1px darkgrey"}) 
	    }
			
		}
	}   
}
