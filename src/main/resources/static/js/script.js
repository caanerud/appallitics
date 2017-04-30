$(document).ready(function(){
  $('.hamburger-trigger').click(function(){
    $('.hamburger-nav').slideToggle();
  });
});

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
