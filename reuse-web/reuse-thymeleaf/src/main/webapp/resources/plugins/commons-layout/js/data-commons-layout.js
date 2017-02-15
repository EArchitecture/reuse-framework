// credits to http://mathiasbynens.be/notes/localstorage-pattern
var DataCommonsLayout = {
    hasStorage : function() {
        if (typeof (Storage) !== "undefined") {
            return true;
        } else {
            return false;
        }
    },
    /**
     * Recurpera o objeto armazenado
     * 
     * @param _key
     *            Chave do objeto armazenado.
     */
    get : function(_key) {
        if (DataCommonsLayout.hasStorage()) {
            return localStorage.getItem(_key);
        } else {
            return getCookie(_key);
        }
    },
    put : function(_key, _value) {
        if (DataCommonsLayout.hasStorage()) {
            localStorage.setItem(_key, _value);
        } else {
            setCookie(_key, _value, 1);
        }
    },
    setCookie : function(cname, cvalue, exdays) {
        var d = new Date();
        d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
        var expires = "expires=" + d.toUTCString();
        document.cookie = cname + "=" + cvalue + "; " + expires;
    },
    getCookie : function(cname) {
        var name = cname + "=";
        var ca = document.cookie.split(';');
        for (var i = 0; i < ca.length; i++) {
            var c = ca[i];
            while (c.charAt(0) == ' ')
                c = c.substring(1);
            if (c.indexOf(name) == 0)
                return c.substring(name.length, c.length);
        }
        return "";
    }
};