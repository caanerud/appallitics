// ACCORDION MENU
$(document).ready(function(){
  $('.hamburger-trigger').click(function(){
    $('.hamburger-container').slideToggle();
  });

  // $('.logo').click(function(){
  //   $('.hamburger-container').slideUp();
  // });
});



// SWITCH BETWEEN LOGIN AND CREATE ACCOUNT
$(document).ready(function(){
  $('.go-to-create-account').click(function(){
    $('.login').fadeOut(100);
    $('.create-account').delay(100).fadeIn(100);
  });

  $('.go-to-login').click(function(){
    $('.create-account').fadeOut(100);
    $('.login').delay(100).fadeIn(100);
  });
});



// CREATE FORM NAVIGATION
$(document).ready(function(){

  $('#choice1').click(function(){
    $(this).parent().parent().delay(100).hide(0);
    $('#overview').delay(100).show(0);
    $('#overview .pro-bar .step:last-of-type').delay(500).animate({width:'25%'});
  });

  $('#choice2').click(function(){
    var href = $(this).attr('href');
    setTimeout(function() {window.location = href}, 100);
    return false;
  });

  $('.next').click(function(){
    $(this).parent().parent().hide();
    $(this).parent().parent().next().show();
    $(this).parent().parent().children('.pro-bar').children('.step:first-of-type').animate({width:'25%'});
    $(this).parent().parent().next().children('.pro-bar').children('.step:last-of-type').animate({width:'25%'});
  });

  $('.prev').click(function(){
    $(this).parent().parent().hide();
    $(this).parent().parent().prev().show();
    $(this).parent().parent().prev().children('.pro-bar').children('.step:first-of-type').animate({width:'0%'});
    $(this).parent().parent().children('.pro-bar').children('.step:last-of-type').animate({width:'0%'});
  });

});



// SMOOTH SCROLLING, BABY
$(document).ready(function(){
  // Add smooth scrolling to all links
  $(".scrollDown").on('click', function(event) {

    // Make sure this.hash has a value before overriding default behavior
    if (this.hash !== "") {
      // Prevent default anchor click behavior
      event.preventDefault();

      // Store hash
      var hash = this.hash;

      // Using jQuery's animate() method to add smooth page scroll
      // The optional number (800) specifies the number of milliseconds it takes to scroll to the specified area
      $('html, body').animate({
        scrollTop: $(hash).offset().top -50
      }, 800, function(){

        // Add hash (#) to URL when done scrolling (default click behavior)
        window.location.hash = hash;
      });
    } // End if
  });
});


// LEADERBOARD LOAD MORE FUNCTIONALITY
$(document).ready(function(){

  $(function () {
    $("tr").slice(0, 10).slideDown();
    $("#loadMore").on('click', function (e) {
        // e.preventDefault();
        $("tr:hidden").slice(0, 10).slideDown();

        // NOT SURE I NEED THIS PART
        // if ($("tr:hidden").length == 0) {
        //     $("#load").fadeOut('slow');
        // }

        // SCROLL TO THE END OF THE NEW SET OF DICTATORS ON CLICK
        $('html,body').animate({
            scrollTop: $(this).offset().top
        }, 1500);
    });
  });

});

// $("#pledge-button").click(function(e) {
//     e.preventDefault();
//     $(this).attr("disabled", "disabled");
//     var count = document.getElementById("pledge");
//     var newCount = parseInt(count.textContent)+1;
//     count.textContent = newCount;
//     $.post("/pledge-click",
//         {
//             count: newCount,
//         }
//     );
//     $("#result").append("Pause.<br>");
//
//
//     setTimeout(function() {
//         $("#pledge-button").removeAttr("disabled");
//     }, 6000 * 10);
// });
//
// $("#revolt-button").click(function(e) {
//     e.preventDefault();
//     $(this).attr("disabled", "disabled");
//    var count = document.getElementById("revolt");
//    var newCount = parseInt(count.textContent)+1;
//    count.textContent = newCount;
//    $.post("/revolt-click",
//        {
//          count: newCount,
//        }
//    );
//     $("#result").append("Pause.<br>");
//
//
//     setTimeout(function() {
//         $("#revolt-button").removeAttr("disabled");
//     }, 6000 * 10);
// });


// //Facebook link from developers
//     <script>(function(d, s, id) {
//         var js, fjs = d.getElementsByTagName(s)[0];
//         if (d.getElementById(id)) return;
//         js = d.createElement(s); js.id = id;
//         js.src = "//connect.facebook.net/en_US/sdk.js#xfbml=1&version=v2.9";
//         fjs.parentNode.insertBefore(js, fjs);
//     }(document, 'script', 'facebook-jssdk'));</script>

// // Twitter link from developers
// <script>window.twttr = (function(d, s, id) {
//     var js, fjs = d.getElementsByTagName(s)[0],
//         t = window.twttr || {};
//     if (d.getElementById(id)) return t;
//     js = d.createElement(s);
//     js.id = id;
//     js.src = "https://platform.twitter.com/widgets.js";
//     fjs.parentNode.insertBefore(js, fjs);
//
//     t._e = [];
//     t.ready = function(f) {
//         t._e.push(f);
//     };
//
//     return t;
// }(document, "script", "twitter-wjs"));</script>