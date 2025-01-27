#ifndef HTTPSTATUS_H
#define HTTPSTATUS_H

namespace com::cjmobileapps::quidditchplayers::network::HttpStatus {
    constexpr int HTTP_ACCEPTED = 202;
    constexpr int HTTP_BAD_GATEWAY = 502;
    constexpr int HTTP_BAD_METHOD = 405;
    constexpr int HTTP_BAD_REQUEST = 400;
    constexpr int HTTP_CLIENT_TIMEOUT = 408;
    constexpr int HTTP_CONFLICT = 409;
    constexpr int HTTP_CREATED = 201;
    constexpr int HTTP_ENTITY_TOO_LARGE = 413;
    constexpr int HTTP_FORBIDDEN = 403;
    constexpr int HTTP_GATEWAY_TIMEOUT = 504;
    constexpr int HTTP_GONE = 410;
    constexpr int HTTP_INTERNAL_ERROR = 500;
    constexpr int HTTP_LENGTH_REQUIRED = 411;
    constexpr int HTTP_MOVED_PERM = 301;
    constexpr int HTTP_MOVED_TEMP = 302;
    constexpr int HTTP_MULT_CHOICE = 300;
    constexpr int HTTP_NOT_ACCEPTABLE = 406;
    constexpr int HTTP_NOT_AUTHORITATIVE = 203;
    constexpr int HTTP_NOT_FOUND = 404;
    constexpr int HTTP_NOT_IMPLEMENTED = 501;
    constexpr int HTTP_NOT_MODIFIED = 304;
    constexpr int HTTP_NO_CONTENT = 204;
    constexpr int HTTP_OK = 200;
    constexpr int HTTP_PARTIAL = 206;
    constexpr int HTTP_PAYMENT_REQUIRED = 402;
    constexpr int HTTP_PRECON_FAILED = 412;
    constexpr int HTTP_PROXY_AUTH = 407;
    constexpr int HTTP_REQ_TOO_LONG = 414;
    constexpr int HTTP_RESET = 205;
    constexpr int HTTP_SEE_OTHER = 303;
    [[deprecated("Use HTTP_INTERNAL_ERROR instead")]]
    constexpr int HTTP_SERVER_ERROR = 500;
    constexpr int HTTP_UNAUTHORIZED = 401;
    constexpr int HTTP_UNAVAILABLE = 503;
    constexpr int HTTP_UNSUPPORTED_TYPE = 415;
    constexpr int HTTP_USE_PROXY = 305;
    constexpr int HTTP_VERSION = 505;
}
#endif // HTTPSTATUS_H
