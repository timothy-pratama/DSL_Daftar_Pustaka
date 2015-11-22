var sb = sb || {};
sb.user = sb.user || {};

sb.isIE = (navigator.appName == "Microsoft Internet Explorer");

sb.pageType = "";
sb.anchorClicked = undefined;

sb.trackException = function (e, name) {
   try {
      var time2 = (new Date()).getTime();
      $.ajax({ type: 'GET', url: "/track", async: false, timeout: 100, data: { c: 'Bug-Exception-' + name, a: e.message, l: e.fileName ? e.fileName : e.filename, v: e.lineNumber, u: encodeURIComponent(window.location), t1: sb.time, t2: time2 } });
   }
   catch (e) { }
}

sb.track = function (sendGA, category, action, label, value) {
   try {
      var time2 = (new Date()).getTime();

      if (action == null || action == '' || action == undefined)
         action = label;

      var url = window.location;
      
      if (category == 'Iframe') {
         url = document.referrer;
         action = $.url(document.referrer).attr('host');
         label = window.location.toString();
      }

      $.ajax({ type: 'GET', url: "/track", async: false, timeout: 100, data: { c: category, a: action, l: label, v: value, u: encodeURIComponent(url), t1: sb.time, t2: time2 } });
      if (sendGA) {
         if (category == 'Iframe')
            ga('send', 'event', category, $.url(document.referrer).attr('host'), document.referrer, time2 - sb.time);
         else
            ga('send', 'event', category, action, label, time2 - sb.time);
      }
   }
   catch (e) {
      sb.trackException(e, 'Track');
   }
}

sb.trackAnchor = function (anchor) {
   try {
      var href = anchor.attr('href');
	  
	  if (href == undefined)
         return;

      var internal = (href.charAt(0) == '#' || href.charAt(0) == '/' || href.indexOf('sciencebuddies.org') >= 0);

      if (anchor.parents('#tabs').length == 1)
         sb.track(true, 'Tab', unescape(anchor.text()), href);
      else if (anchor.parents('#subtabs').length == 1) {
         sb.track(true, 'SubTab' + (sb.pageType ? "-" + sb.pageType : ""), unescape(anchor.text()), href, sb.scrollMax);
         sb.scrollMax = 0;
      }
      else if (anchor.parents('#header').length == 1)
         sb.track(true, 'Header', unescape(anchor.text()), href);
      else if (anchor.parents('#footer').length == 1)
         sb.track(true, 'Footer', unescape(anchor.text()), href);
      else if (anchor.parents('#menu-icons').length == 1)
         sb.track(true, 'MenuIcons', unescape(anchor.text()), href);
      else if (anchor.parents('#menu-icons-share').length == 1)
         sb.track(true, 'ShareIcons', unescape(anchor.text()), href);
      else if (anchor.parents('#menu-share').length == 1)
         sb.track(true, 'ShareMenu', unescape(anchor.text()), href);
      else if (anchor.parents('#sponsors').length == 1)
         sb.track(true, 'Sponsors', unescape($(anchor).parents('.sponsor').first().attr('id')), href);

      if (internal) {
         var url = anchor.url();
         var file = url.attr('file').toLowerCase();

         if (file.indexOf('.pdf') >= 0 || file.indexOf('.doc') >= 0 || file.indexOf('.xls') >= 0 || file.indexOf('.ppt') >= 0 || file.indexOf('.zip') >= 0 || file.indexOf('.wav') >= 0 || file.indexOf('.java') >= 0)
            sb.track(true, 'Download', file, href);

         return;
      }

      if (href.indexOf('http://') == 0 || href.indexOf('https://') == 0) {
         sb.track(true, 'External', unescape(anchor.text()), href);
         return;
      }
   }
   catch (e) {
      sb.trackException(e, 'TrackAnchor');
   }
}

sb.click = function(anchor) {
   anchor = anchor[0];
   if (document.createEvent) {
	   var event = document.createEvent("MouseEvents"); 
	   event.initMouseEvent("click", true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null); 
	   anchor.dispatchEvent(event);
   }
   else if(document.createEventObject) {
	   var event = document.createEventObject();
	   anchor.fireEvent("onclick", event);
   }
}

sb.redirect = function(url) {
   $('#updateProgress').addClass('ajax-show');
   if (parent)
      parent.window.location.href = url;
   else
      window.location.href = url;
}

try {
   window.addEventListener("message", receiveMessage, false);
}
catch (e) {
}

function receiveMessage(event)
{
   var s = event.origin.split(".");
   if (s.length < 2 || s[s.length-1] != "org" || s[s.length-2] != "sciencebuddies")
      return;

   $('.sb-mfp-login iframe').css('height', (event.data + 0) + 'px');
}

sb.resizeLogIn = function() {
   var newHeight = document.body.offsetHeight;
   parent.postMessage(newHeight, '*');
}

sb.resizeLogInSetup = function() {
   sb.resizeLogIn();
   onElementHeightChange(document.body, function(){
      sb.resizeLogIn();
   });
}

sb.pinterest = function () {
   sb.click($('span.pinterest-button > a'));
}

sb.setActiveStyleSheet = function (title) {
   var links = document.getElementsByTagName("link");
   for (i = 0; i < links.length; i++) {
      var link = links[i];
      if (link.getAttribute("rel").indexOf("style") != -1 && link.getAttribute("title")) {
         link.disabled = true;
         if (link.getAttribute("title") == title)
            link.disabled = false;
      }
   }
}


sb.beforePrint = function () {
}

sb.afterPrint = function () {
}

if (window.matchMedia) {
   var mediaQueryList = window.matchMedia('print');
   mediaQueryList.addListener(function (mql) {
      if (mql.matches)
         sb.beforePrint();
      else
         sb.afterPrint();
   });
}

window.onbeforeprint = sb.beforePrint;
window.onafterprint = sb.afterPrint;

var airbrake = new airbrakeJs.Client({ projectId: 116444, projectKey: '0e746d7664e9de4d783df320dbded5a9' });

window.onerror = function (msg, url, line) {
   try {
      if (url == '' || url.substring(1, 1) != "/")
         return;

      if (Math.random() <= 0.1)
         airbrake.notify({ error: { message: msg, fileName: url, lineNumber: line }, context: { environment: 'production' } });
      sb.track(true, 'Bug-Undefined', msg, url, line);
   }
   catch (e) { }
}

$(window).unload(function () {
   sb.track(false, 'PageView', 'End', '', sb.scrollMax);
});

sb.mobileMatch = navigator.userAgent.match(/(iPad)|(iPhone)|(iPod)|(Silk)|(android)|(webOS)/i);
sb.isMobile = (sb.mobileMatch != null && sb.mobileMatch.length > 0);
sb.emptySearch = 'Search sciencebuddies.org';

sb.hashChangeCallbacks = $.Callbacks( );

$(function () {
   if (typeof (eopdf) == "object") {
      sb.setActiveStyleSheet('print');
      $('.show-pdf').removeClass('only-screen');
      $('.hide-pdf').addClass('only-screen');
   }

   sb.time = (new Date()).getTime();
   sb.track(false, 'PageView', 'Start');

   if (parent !== window && !sb.isIE)
      sb.track(true, 'Iframe');

   $('#subtabs').show();

   $.fn.hashchange.src = '/document-domain.html';
   $.fn.hashchange.domain = document.domain;

   $('a').live('click', function (event) {
      sb.anchorClicked = $(this);
      sb.trackAnchor($(this));
   });

   $(window).hashchange(function () {
      var hash = location.hash;

      var tab;
      if (hash != '') {
         hash = '#subtab-' + hash.substring(1);
         tab = $('.tab > a[href="#' + hash.substring(8) + '"]');
         if (tab.attr('href') == undefined)
            hash = '';
         else if (sb.anchorClicked != undefined) {
            if (sb.anchorClicked.parents('#subtabs').length == 0)
               document.getElementById('subtabs').scrollIntoView(true);
         }
      }
      sb.anchorClicked = undefined;

      var scroll = undefined;
      if (hash == '') {
         var dest = $('#' + location.hash.substring(1));

         if (dest.attr('id') != undefined) {
            tab = dest.parents('.subtab-section').first();
            hash = '#' + tab.attr('id');
            tab = $('.tab > a[href="#' + hash.substring(8) + '"]');
            scroll = dest;
         }
      }

      if (hash == '') {
         var subtabId = $('.subtab-section').first().attr('id');

         if (subtabId != undefined) {
            hash = '#' + subtabId;
            tab = $('.tab > a[href="#' + hash.substring(8) + '"]');
         }
      }

      if (tab != undefined && tab.parent().hasClass('tab')) {
         $('#subtabs > .tab').removeClass('selected');
         tab.parent().addClass('selected');
      }

      $('.subtab-section').hide();
      $('.subtab-section-dependant').hide();
      if (hash != '') {
         $(hash).show();
         $('.' + hash.substring(1) + '-dependant').show();
      }
      if (scroll != undefined)
         document.getElementById(scroll.attr('id')).scrollIntoView(true);

      sb.hashChangeCallbacks.fire(hash);
   })
   $(window).hashchange();

   $('.email-link').attr('href', '/science-fair-projects/email_this.php?filename=' + encodeURIComponent(location.pathname + location.search) + "&title=" + encodeURIComponent(document.title));
   $('.print-link').attr('href', 'javascript:window.print();');
   $('.report-link').attr('href', '/science-fair-projects/email-somethingwrong.shtml?from=' + encodeURIComponent(location.pathname));
   $('.facebook-link').attr('href', 'http://www.facebook.com/sharer/sharer.php?u=' + encodeURIComponent(location));
   $('.facebook-link').attr('target', '_blank');
   $('.twitter-link').attr('href', 'http://twitter.com/intent/tweet?status=' + encodeURIComponent(document.title) + ': ' + encodeURIComponent(location));
   $('.twitter-link').attr('target', '_blank');
   $('.google-link').attr('href', 'https://plus.google.com/share?url=' + encodeURIComponent(location) + '&' + encodeURIComponent(document.title));
   $('.google-link').attr('target', '_blank');
   $('.pinterest-link').live('click', function (event) { sb.pinterest(); return false; });

   sb.trackCitation('.citation-mla', 'MLA');
   sb.trackCitation('.citation-apa', 'APA');
   
   var url = $.url(location.toString());
   var rop = url.param('rop');

   if (!(rop == undefined))
      $('.rop-link').attr('href', function () { return $(this).attr('href') + ($(this).attr('href').indexOf('?') < 0 ? '?' : '&') + 'rop=' + rop; });

   $("#search-input").focus(function () {
      searchFocus(true);
   });

   $("#search-input").blur(function () {
      searchFocus(false);
   });

   searchFocus(false);

   $('table.data > tbody > tr:odd').addClass('odd');
   $('table.data > tbody > tr:even').addClass('even');
   $('table.data > tbody > tr:first-child').addClass('first');

   $("#search-form").submit(function () {
      if ($('#search-input').val() == sb.emptySearch)
         $('#search-input').val('')

      if ($('#search-input').val() == '') {
         $('#search-empty-background').show();
         $('#search-empty').show();
         return false;
	  }
   });
   
   $("#search-empty").click(function() { $('#search-empty').hide(); $('#search-empty-background').hide(); $('#search-input').focus(); });
   $("#search-empty-background").click(function() { $('#search-empty').hide(); $('#search-empty-background').hide(); $('#search-input').focus(); });

   $('a[href*="/account/login"]').each(function() { 
      $(this).attr('href', $(this).attr('href').replace("/account/login", "/account/login-popup"));
      $(this).magnificPopup({ 
	     type: 'iframe',
	     mainClass: 'sb-mfp-login sb-mfp-login-wide',
	     closeBtnInside: true,
	     overflowY: 'hidden',
         closeOnBgClick: false
      });
   });

   $('.login-link').attr('href', 'https://' + location.hostname + '/account/login-only-popup?t=' + sb.user.token);
   try {
      $('.login-link').magnificPopup({ 
	     type: 'iframe',
	     mainClass: 'sb-mfp-login',
	     closeBtnInside: true,
	     overflowY: 'hidden',
	     closeOnBgClick: false
      });
   }
   catch (e) {}

   try {
      $('.login-link-no-popup').attr('href', '/account/login?url=' + encodeURIComponent(parent.window.location.pathname + parent.window.location.search));
   }
   catch (e) {}
   
   $('.logout-link').live('click', function (event) {
      $.getJSON("/science-fair-projects/logout_member.php",
                function (data) {
                   window.location.reload();
                });
      return false;
   });

   $("#search-input").keypress(function (event) {
      if (event.charCode == 117 && event.ctrlKey && event.altKey) {
         xxx();
      }
   });
});

function popupWindow(url, w, h) {
   if (!w)
      w = 720;
   if (!h)
      h = 690;
   window.open(url, 'popupWindow', 'toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no,width=' + w + ',height=' + h + ',top=70,left=175')
}

function popupVideo(url, w, h) {
   if (!w)
      w = 720;
   if (!h)
      h = 690;
   window.open(url, 'popupWindow', 'toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no,width=' + w + ',height=' + h + ',top=70,left=175')
}

function searchFocus(focus) {
   if (focus) {
      if ($("#search-input").val() == sb.emptySearch)
         $("#search-input").val('');
      $('#search-input').removeClass('empty');
   }
   else if ($("#search-input").val() == '') {
      $("#search-input").val(sb.emptySearch);
      $('#search-input').addClass('empty');
   }
}

function searchFilter(anchor, limit) {
   $('#search-text').html($(anchor).html());
   $('#search-query').val(limit);
   $('#search-dropdown').hide();

   $("#search-form").submit();

   return false;
}

function searchSet(text, filter, query) {
   if (text == '')
      text = sb.emptySearch;

   $('#search-text').html(filter);
   $('#search-input').val(htmlDecode(text));
   $('#search-query').val(query);

   return false;
}

function htmlEncode(value) {
   return $('<div/>').text(value).html();
}

function htmlDecode(value) {
   return $('<div/>').html(value).text();
}

if (typeof String.prototype.trim !== 'function') {
   String.prototype.trim = function () {
      return this.replace(/^\s+|\s+$/g, '');
   }
}

sb.favoriteProjectIdea = function (anchor, pi) {
   var add = $(anchor).hasClass('add-favorite');

   if (add) {
      $.ajax({ type: 'GET', url: "/Handlers/FavoriteProjectIdeas.aspx", async: true, data: { pi: pi, op: 'add' } });
      $(anchor).removeClass('add-favorite');
      $(anchor).addClass('remove-favorite');
   }
   else {
      $.ajax({ type: 'GET', url: "/Handlers/FavoriteProjectIdeas.aspx", async: true, data: { pi: pi, op: 'remove' } });
      $(anchor).removeClass('remove-favorite');
      $(anchor).addClass('add-favorite');
   }
}

sb.isScrolledIntoView = function(elem)
{
   var docViewTop = $(window).scrollTop();
   var docViewBottom = docViewTop + $(window).height();

   var elemTop = $(elem).offset().top;
   var elemBottom = elemTop + $(elem).height();

   return ((elemBottom <= docViewBottom) && (elemTop >= docViewTop));
}

sb.scrollMax = 0;
$(window).scroll(function () {
   var height = $(window).height();
   var top = $(window).scrollTop();
   var bottom = top + height;
   var docHeight = $(document).height()

   percentage = Math.round(100 * bottom / docHeight);
   if (percentage > sb.scrollMax) {
      sb.scrollMax = percentage;
   }
});

sb.getCookie = function (name) {
   name = name + "=";
   var ca = document.cookie.split(';');
   for (var i = 0; i < ca.length; i++) {
      var c = ca[i].trim();
      if (c.indexOf(name) == 0)
         return c.substring(name.length, c.length);
   }

   return "";
}

sb.setCookie = function (name, value) {
   document.cookie = name + "=" + value + "; path=/"
}

sb.inputLimitLength = function(input) {
   input = $(input);
   var length = input.attr('maxlength');
   if (input.val().length > length)
      input.val(input.val().substring(0, length));
   else
	  input.next('.input-limit-length').html((length - input.val().length) + ' characters left.');
}

sb.trackCitation = function (element, label) {
   $(element).bind('mouseup', function(e){
      var selection;

      if (window.getSelection) {
         selection = window.getSelection();
      } else if (document.selection) {
         selection = document.selection.createRange();
      }


      if (selection.toString() !== '')
         sb.track(true, 'Citation', 'Select', label);
   });
}

sb.openSurvey = function() {
   var surveyBackground = document.getElementById('survey-background'); 
   var survey = document.getElementById('survey'); 

   if (surveyBackground != null) {
      surveyBackground.style.display = 'block';
      survey.style.display = 'block';
      sb.track(true, 'Popup-Survey', 'Open');
   }
}

sb.closeSurvey = function(skipTrack) {
   var surveyBackground = document.getElementById('survey-background'); 
   var survey = document.getElementById('survey'); 

   if (surveyBackground != null) {
      surveyBackground.style.display = 'none';
      survey.style.display = 'none';
	  if (!skipTrack)
         sb.track(true, 'Popup-Survey', 'Cancel');
   }
}

sb.submitSurvey = function(completed) {
   var form_data = $("#survey").serialize() + '&survey-completed=' + (completed ? 1 : 0) + "&survey-url=" + encodeURIComponent(window.location);

   jQuery.post("/Handlers/Surveys/PutSurvey.ashx", form_data,
      function (data) {
         if (completed) {
            $('#survey-message').html(data.message);
            if (data.success) {
               $('#survey-message').css('font-size', '22px').css('text-align', 'center');
			   $('#survey-close').hide();
			   $('#survey-cancel').hide();
			   $('#survey-submit').hide();
               sb.track(true, 'Popup-Survey', 'Submit');
               setTimeout(function() { sb.closeSurvey(true); }, 1000);
            }
         }
      }, "json");

   return false;
}

$(function () {
   if (!$('#aspnetForm').length) {
      var legend = $("#survey-objective-fields legend");

      $("#survey-objective-fields").randomize("div.randomize");
      $("#survey-objective-fields").prepend(legend);

      $('#survey-submit').click(function () { sb.submitSurvey(true); });
      $('#survey-close').click(function () { sb.submitSurvey(false); });
      $('#survey-cancel').click(function () { sb.submitSurvey(false); });
      sb.openSurvey();
   }
});

(function($) {

jQuery.fn.randomize = function(childElem) {
  return this.each(function() {
      var _this = $(this);
      var elems = _this.children(childElem);

      elems.sort(function() { return (Math.round(Math.random())-0.5); });  

      _this.remove(childElem);  

      for(var i=0; i < elems.length; i++)
        _this.prepend(elems[i]);      

  });    
}
})(jQuery);

function onElementHeightChange(elm, callback){
    var lastHeight = elm.clientHeight, newHeight;
    (function run(){
        newHeight = elm.clientHeight;
        if( lastHeight != newHeight )
            callback();
        lastHeight = newHeight;

        if( elm.onElementHeightChangeTimer )
            clearTimeout(elm.onElementHeightChangeTimer);

        elm.onElementHeightChangeTimer = setTimeout(run, 1000);
    })();
}

function receiveCount(obj)
{
// Pinterest callback
//   console.log(obj.url);
//   console.log(obj.count);
}
