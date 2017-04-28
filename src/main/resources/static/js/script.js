$(document).ready(function(){
  $('.hamburger-trigger').click(function(){
    $('.hamburger-nav').slideToggle();
  });
});

$(document).ready(function(){
  $('.go-to-create-account, .go-to-login').click(function(){
    $('.login, .create-account').toggle();
  });
});
