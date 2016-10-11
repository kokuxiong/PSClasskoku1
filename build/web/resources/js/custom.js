var resetCheckbox = function () {
    
    var $swc = $("[switch-check]");
    if($swc.size() > 0){
        $swc.bootstrapSwitch();
    }
};

var initDatePicker = function () {
    $("[datepicker]").datetimepicker();
}

var resetPlugin = function () {
    initDatePicker();
    resetCheckbox();

}
$(function () {
    resetPlugin();
});