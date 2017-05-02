// ACCORDION MENU
$(document).ready(function(){
  $('.hamburger-trigger').click(function(){
    $('.hamburger-nav').slideToggle();
  });
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
  $('#overview').show();
  $('#overview .pro-bar .step:last-of-type').animate({width:'25%'});


  $('.next').click(function(){
    $(this).parent().parent().hide();
    $(this).parent().parent().next().show();
    // $(this).parent().parent().next().ready(function(){
    //   $('.step:last-of-type').animate({width:'25%'});
    // });
  });

  $('.prev').click(function(){
    $(this).parent().parent().hide();
    $(this).parent().parent().prev().show();
    // $(this).parent().parent().prev().ready(function(){
    //   $('.step:last-of-type').css('width','0%');
    // });
    // $(this).parent().parent().next().ready(function(){
    //   $('.step:last-of-type').animate({width:'25%'});
    // });
  });
  
});
