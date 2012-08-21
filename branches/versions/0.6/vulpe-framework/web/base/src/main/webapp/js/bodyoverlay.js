function getPageSize(){
	var xScroll, yScroll;
	if (window.innerHeight && window.scrollMaxY) {
		xScroll = document.body.scrollWidth;
		yScroll = window.innerHeight + window.scrollMaxY;
	} else
	if (document.body.scrollHeight > document.body.offsetHeight){ // all but Explorer Mac
		xScroll = document.body.scrollWidth;
		yScroll = document.body.scrollHeight;
	} else { // Explorer Mac...would also work in Explorer 6 Strict, Mozilla and Safari
		xScroll = document.body.offsetWidth;
		yScroll = document.body.offsetHeight;
	}

	var windowWidth, windowHeight;
	if (self.innerHeight) {	// all except Explorer
		windowWidth = self.innerWidth;
		windowHeight = self.innerHeight;
	} else
	if (document.documentElement && document.documentElement.clientHeight) { // Explorer 6 Strict Mode
		windowWidth = document.documentElement.clientWidth;
		windowHeight = document.documentElement.clientHeight;
	} else
	if (document.body) { // other Explorers
		windowWidth = document.body.clientWidth;
		windowHeight = document.body.clientHeight;
	}

	// for small pages with total height less then height of the viewport
	if(yScroll < windowHeight){
		pageHeight = windowHeight;
	} else {
		pageHeight = yScroll;
	}

	// for small pages with total width less then width of the viewport
	if(xScroll < windowWidth){
		pageWidth = windowWidth;
	} else {
		pageWidth = xScroll;
	}
	return new Array(pageWidth,pageHeight,windowWidth,windowHeight);
}

function showDiv(div, container) {
	var sizesPage = getPageSize();
	var divObj = vulpe.util.getElement(div);
	divObj.style.display = 'block';
	divObj.style.height = sizesPage[1] + 'px';
	divObj.style.width = sizesPage[0] + 'px';
	try{
		if (typeof container != "undefined")
			WCH.Apply(div, container);
		else
			WCH.Apply(div, 'container');
	}catch(eWCH){
		// do nothing
	}
}

function hideDiv(div, container) {
	vulpe.util.getElement(div).style.display = 'none';
	try{
		if (typeof container != "undefined")
			WCH.Discard(div, container);
		else
			WCH.Discard(div, 'container');
	}catch(eWCH){
		// do nothing
	}
}