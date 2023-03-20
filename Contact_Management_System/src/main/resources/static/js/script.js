console.log("This is contact management system");

const toggleSidebar = () => {
if($(".sidebar").is(":visible"))
{
    //if true -> close the tab
    $(".sidebar").css("display","none");
    $(".content").css("margin-left","0%");

} else{
    //if flase -> open the tab (show the tab)

    $(".sidebar").css("display","block");
    $(".content").css("margin-left","20%");
}

};