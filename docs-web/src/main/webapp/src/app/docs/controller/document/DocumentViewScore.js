'use strict';

/**
 * Document view (activity for now) controller.
 */
angular.module('docs').controller('DocumentViewScore', function ($scope, $stateParams, Restangular, $state) {
  $scope.addScore = function () {
    var score = document.getElementById("scoreContent").value;
    if (!isNaN(score)) {
      if (parseInt(score) >= 0 && parseInt(score) <= 100) {
        var documentId = document.getElementById("documentId").innerHTML;
        Restangular.one('documentscore/' + documentId).post('', {score: score}).then(function (){document.getElementById("scoreDiv").innerHTML="score: "+score});
        location.reload()
        document.getElementById("errorMsg").innerHTML=""
        document.getElementById("popup").style.visibility = "hidden";
      } else {
        document.getElementById("errorMsg").innerHTML="Invalid score. Score must be between 0 and 100."
        document.getElementById("popup").style.visibility = "hidden";
        document.getElementById("form").style.visibility = "visible";
      } 
    } else {
      document.getElementById("errorMsg").innerHTML="Invalid score. Score must be a numeric value between 0 and 100."
      document.getElementById("popup").style.visibility = "hidden";
      document.getElementById("form").style.visibility = "visible";
    }
  };
}); 