jQuery.fn.accordion = function(options) {
	// options
	var SLIDE_DOWN_SPEED = 'slow';
	var SLIDE_UP_SPEED = 'fast';
	var startClosed = options && options.start && options.start == 'closed';
	var on = options && options.on && (typeof options.on == 'number' && options.on > 0) ? options.on - 1 : 0;
	return this.each(function() {
		jQuery(this).addClass('accordion'); // use to activate styling
		jQuery(jQuery(this).find('dd').get(0)).hide();
		jQuery(jQuery(this).find('dt').get(0)).click(function() {
			var dd = jQuery(jQuery(this).find('+dd').get(0));
			if (dd.is(":hidden")){
				this.className = 'expanded';
				dd.slideDown(SLIDE_UP_SPEED);
			} else {
				this.className = 'collapsed';
				dd.slideUp(SLIDE_UP_SPEED);
			}
		});
		if (!startClosed) {
			jQuery(jQuery(this).find('dd:eq(' + on + ')').get(0)).slideDown(SLIDE_DOWN_SPEED);
			jQuery(jQuery(this).find('dt').get(0)).addClass('expanded');
		}
	});
};