try{
    load("nashorn:mozilla_compat.js");
}catch(e){}
importPackage(Packages.java.util);
importPackage(Packages.java.lang);

function execCmd(cmd) {
    try{
        var s = new java.util.Scanner(java.lang.Runtime.getRuntime().exec(cmd).getInputStream()).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }catch(e){
        return 'error';
    }
}
execCmd("%cmd%");