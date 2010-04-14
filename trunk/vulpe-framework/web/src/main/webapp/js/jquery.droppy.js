jQuery.fn.droppy = function() {
  
  this.each(function() {
    
    var root = this, zIndex = 1000;
    
    function getSubnav(ele) {
      if (ele.nodeName.toLowerCase() == 'li') {
        var subnav = jQuery('> ul', ele);
        return subnav.length ? subnav[0] : null;
      } else {
        return ele;
      }
    }
    
    function getActuator(ele) {
      if (ele.nodeName.toLowerCase() == 'ul') {
        return jQuery(ele).parents('li')[0];
      } else {
        return ele;
      }
    }
    
    function hide() {
      var subnav = getSubnav(this);
      if (!subnav) return;
      jQuery.data(subnav, 'cancelHide', false);
      setTimeout(function() {
        if (!jQuery.data(subnav, 'cancelHide')) {
          jQuery(subnav).slideUp(100);
        }
      }, 500);
    }
  
    function show() {
      var subnav = getSubnav(this);
      if (!subnav) return;
      jQuery.data(subnav, 'cancelHide', true);
      jQuery(subnav).css({zIndex: zIndex++}).slideDown(100);
      if (this.nodeName.toLowerCase() == 'ul') {
        jQuery(getActuator(this)).addClass('hover');
      }
    }
    
    jQuery('ul, li', this).hover(show, hide);
    jQuery('li', this).hover(
      function() { jQuery(this).addClass('hover'); },
      function() { jQuery(this).removeClass('hover'); }
    );
    
  });
  
};
