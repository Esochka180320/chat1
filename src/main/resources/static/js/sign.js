$(function() {
   $(".btn").click(function() {
      $(".form-signin").toggleClass("form-signin-left");
      $(".form-signup").toggleClass("form-signup-left");
      $(".frame").toggleClass("frame-long");
      $(".signup-inactive").toggleClass("signup-active");
      $(".signin-active").toggleClass("signin-inactive");
      $(".forgot").toggleClass("forgot-left");
      $(this).removeClass("idle").addClass("active");
   });
});


$(function() {
   $(".forgot-a").click(function() {
      $(".forgot").html('<form class="form-forgot" action="/send_mail" method="post" name="form"> <label for="email" >Enter your Email</label><input class="form-styling  input-forgot" type="email" name="email" placeholder="" required> <button class="btn-forgot">Send to the mail</button></form> ');

   });
});


$(function() {
   $(".btn-signup").click(function() {
      var passcode_input = document.querySelector(".pass");
      var password_confirm = document.querySelector(".confir");
      var full_name = document.querySelector(".Full_name");


      if (passcode_input.value != password_confirm.value) {
         password_confirm.setCustomValidity("Your passwords don't match.");

      }  else{

         password_confirm.setCustomValidity("");

      }

   });
   $(".confir , .pass, .Full_name").keyup(function() {
      var full_name = document.querySelector(".Full_name");
      var password_confirm = document.querySelector(".confir");
      password_confirm.setCustomValidity("");
      full_name.setCustomValidity("");


   });

});
function test (value){
   var regExp = /^([А-ЯA-ZёЁЇїІіЄєҐґ]|[А-ЯA-ZёЁЇїІіЄєҐґ][\x27а-яa-zёЁЇїІіЄєҐґ]{1,}|[А-ЯA-ZёЁЇїІіЄєҐґ][\x27а-яa-zёЁЇїІіЄєҐґ]{1,}\-([А-ЯA-ZёЁЇїІіЄєҐґ][\x27а-яa-zёЁЇїІіЄєҐґ]{1,}|(оглы)|(кызы)))\040[А-ЯA-ZёЁЇїІіЄєҐґ][\x27а-яa-zёЁЇїІіЄєҐґ]{1,}(\040[А-ЯA-ZёЁЇїІіЄєҐґ][\x27а-яa-zёЁЇїІіЄєҐґ]{1,})?$/
   return regExp.test(value)
}
