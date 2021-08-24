//Get the url of the users current tab, add this to the hidden form input
chrome.tabs.getSelected(null, function(tab) {
        var tabId = tab.id;
        tabURL = tab.url; //URL of current tab
        document.getElementById("pageURL").value = tabURL;

});

window.addEventListener('keyup',function(e){
    if (e.keyCode === 13 && (document.getElementById('searchText').value.length != 0)) {
      document.getElementById("filtersForm").submit();
  }
});

// When page loads
document.addEventListener('DOMContentLoaded', function() {
    var toggleFiltersButton = document.getElementById('showFiltersButton');
    var toggleFiltersSection = document.getElementById('showFiltersButtonAndText');
    var filterText = document.getElementById('showFiltersText');
    var filterBreak = document.getElementById('filterBreak');
    var helpButton = document.getElementById('helpButton');
    var helpPopup = document.getElementById('helpPopup');
    var helpBreak = document.getElementById('helpBreak');
    // onClick's logic below:
    //when user clicks Show Filter button
    toggleFiltersSection.addEventListener('click', function() {
      var filters = document.getElementById('filterDiv');

      var displaySetting = filters.style.display;

      var filtersButton = document.getElementById('toggleFiltersButton');

      //If previously visible
      if (displaySetting == 'block') {
        filters.style.display = 'none';
        filterBreak.style.display = 'none';
        toggleFiltersButton.style.transform = 'scale(1, 1)';
        filterText.innerHTML = "Show Filters"
      }
      else { //If previously not visible
        filters.style.display = 'block';
        filterBreak.style.display = 'block';
        toggleFiltersButton.style.transform = 'scale(1, -1)';
        filterText.innerHTML = "Hide Filters   ";
      }
    });

    //when user clicks on help button
    helpButton.addEventListener('click', function() {
      var displaySetting = helpPopup.style.display;

      //If previously visible, hide help block
      if (displaySetting == 'block') {
        helpPopup.style.display = 'none';
        helpBreak.style.display = 'none';
      }
      else { // If previously hidden, display help block
        helpBreak.style.display = 'block';
        helpPopup.style.display = 'block';
      }
    });
});
