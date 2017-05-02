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
  $('.next').click(function(){
    $(this).parent().parent().hide();
    $(this).parent().parent().next().show();
  });

  $('.prev').click(function(){
    $(this).parent().parent().hide();
    $(this).parent().parent().prev().show();
  });
});

// STATUS BAR ANIMATION
