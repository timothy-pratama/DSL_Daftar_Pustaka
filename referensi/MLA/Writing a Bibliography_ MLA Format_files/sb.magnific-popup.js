$(document).ready(function() {
   $('.popup-gallery').each(function() {
      $(this).magnificPopup({
         delegate: 'a',
         type: 'image',
         tLoading: 'Loading image #%curr%...',
         mainClass: 'mfp-img-mobile',
         fixedContentPos: true,
         fixedBgPos: true,
         gallery: {
            enabled: true,
            navigateByImgClick: true,
            preload: [0,1]
         },
         image: {
            tError: '<a href="%url%">The image #%curr%</a> could not be loaded.',
            titleSrc: function(item) {
               return item.el.attr('title');
            }
         },

         callbacks: {
            close: function() {
               sb.track(true, 'Galleries', $.magnificPopup.instance.items[$.magnificPopup.instance.index].el.parent().attr('data-track'), 'Close');
            },
            change: function() {
               var action = $.magnificPopup.instance.items[$.magnificPopup.instance.index].el.parent().attr('data-track');
               var src = $.magnificPopup.instance.currItem.src.split('/');
               var label = ($.magnificPopup.instance.index + 1) + '/' + $.magnificPopup.instance.items.length + ' ' + src[src.length - 1];

               sb.track(true, 'Galleries', action, label, $.magnificPopup.instance.index + 1);
			   sb.galleriesCurrent[action] = $.magnificPopup.instance.index;
            },
         }

      });
   });
});

sb.galleriesCurrent = [];
sb.openGallery = function(id) { var action = $('#' + id).attr('data-track'); sb.track(true, 'Galleries', action, 'Open'); $('#' + id).magnificPopup('open', sb.galleriesCurrent[action] === undefined ? 0 : sb.galleriesCurrent[action]); }
