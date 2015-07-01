function Chapter(){	
	this.selectedOptionIndex = 0
	this.clickNum = 0
	this.heartScore = 0
	this.starScore = 0
	this.partNum = 0
/*	this.finalNum = 0*/
	this.numAnswers = []
	this.Audio = document.getElementById("Audio")
}

//初始化页面
Chapter.prototype.init = function(){	
	$animeHolder.css('background-image','url(gif/'+ chapterNum +'-1.gif)')
	this.delayTime()
	
	this.Audio.children[0].play()
	var _this = this
	setTimeout(function(){
		_this.Audio.children[1].play()
	},4500)
	this.Audio.children[1].loop = 'loop'	
	this.changeAnimation(this.clickNum)
  this.changeDescription()
  this.changeOption()    
	this.beginDate = new Date()
}
//延迟显示描述和选项
Chapter.prototype.delayTime = function(){
	var _this = this	
	this.fadeTime = window.setTimeout(function(){
		_this.showDescription()
	},500)
	this.showTime = window.setTimeout(function(){
		_this.showOption()
	},500)
}
Chapter.prototype.showDescription = function(){
	$descriptionHolder.css('display','block')
}
Chapter.prototype.showOption = function(){		
	$optionHolder.css('display','block')
}
//当点击数达到5，10，15时显示小节评分页面
Chapter.prototype.showPartScrore = function(){
		if (this.clickNum == questions.length) {
		  clearTimeout(this.fadeTime)
	    clearTimeout(this.showTime)
	    this.hideAll()  
	    this.Audio.children[1].pause()
	    this.endDate = new Date()
	    this.getHeart()
		}
}

Chapter.prototype.getHeart = function(){
  var _this = this
  function formatDate(date) {
    return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate() + 'T' + date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds();
  }
  function countPartHeart(numAnswers){
    Request(ctx + '/egame/countChapterHeart.do',{ ownerId: ownerId, playerId: playerId, chapterNum: chapterNum, beginDate: formatDate(_this.beginDate), endDate: formatDate(_this.endDate), optionIndexes: numAnswers},
        function(data){
      $("#score").html(data.score + "分")
      _this.heartScore = data.heart      
      _this.changeHeart()
      $partScoreHolder.css('display','block')
    },function(){
      $ErrorContainer.css('display','block')
      $ErrorBtn.off('click')
      $ErrorBtn.click(function(){
        $ErrorContainer.css('display','none')
        countPartHeart(partNum)
      })
    })
  }
	
  countPartHeart(_this.numAnswers)

}


Chapter.prototype.changeHeart = function(){
	if(this.heartScore == 5){
		this.starScore ++
		$speak.html("太棒了，老朽佩服！")
		$PS.css({'background':'url(gif/ps02.gif) no-repeat','background-position':'center'})
	}else{
		$speak.html("小伙子，再加把劲！")
		$PS.css({'background':'url(gif/ps01.gif) no-repeat','background-position':'center'})
	}
	//根据心形得分值把空心的心形设置成实心的
	for(var i=0;i<5;i++){
		$heart[i].className = 'heartEmpty'
	}
	tickNum = this.heartScore
	Tick()
}

Chapter.prototype.hideOption = function(){
	$optionHolder.css('display','none')
	$confirm.css('display','none')
}
Chapter.prototype.hideAll = function(){
	$animeHolder.css('display','none')
	$descriptionHolder.css('display','none')
}
Chapter.prototype.changeOption = function(){
  $optionTitle.html(questions[this.clickNum].title) 
	//重置圆圈的点击效果显示状态
	for(var i=0;i<$circle.length;i++){
		$circle[i].className = "circle"
		$optionContent[i].innerHTML = questions[this.clickNum].options[i]
	}
}
Chapter.prototype.changeDescription = function(){
	$descriptionHolder.css('display','none')
	$descriptionHolder.html(questions[this.clickNum].description)
}
Chapter.prototype.changeAnimation = function(num){
	$animeHolder.css('background-image','url(gif/'+chapterNum+'-'+(num+1)+'.gif)')
}
Chapter.prototype.saveOption = function(){		
	this.numAnswers.push(ScoreStorage.NUM)
}
Chapter.prototype.selectOption = function(ev){
	if(ev.target.className == "circle"){
		for(var i=0;i<$circle.length;i++){
			$circle[i].className = 'circle'
			if(ev.target.id === $circle[i].id){
				this.selectedOptionIndex = i
			}
		}
	}else if(ev.target.className == "optionContent"){
		for(var j=0;j<$optionContent.length;j++){
			$circle[j].className = 'circle'
			if(ev.target.id === $optionContent[j].id){
				this.selectedOptionIndex = j
			}
		}		
	}
	//设置当前选择的选项圆圈显示效果
	$circle[this.selectedOptionIndex].className = 'circleClick'
	$confirm.css('display','inline-block')
	//记录选择的选项
	ScoreStorage.NUM = this.selectedOptionIndex
}

//重置所有心形为空心
Chapter.prototype.resetHeart = function(){
	this.heartScore = 0
	for(var i=0;i<$heart.children.length;i++){
		$heart[i].className = 'heartEmpty'
	}
}
Chapter.prototype.toggleAudio = function(){
	if($SoundBtn.html() == "静音"){
	  this.Audio.children[0].pause()
		this.Audio.children[1].pause()
		$SoundBtn.html("音乐")
	}else if($SoundBtn.html() == "音乐"){
		this.Audio.children[1].play()
		$SoundBtn.html("静音")
	}	
}
