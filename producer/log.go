package main

import (
	"fmt"
	"math/rand"
	"time"
)

type Log struct {
	IP               string
	DateTime         string
	LogLevel         string
	HttpMethod       string
	HttpResponseCode int
	RequestTimeTaken int
}

var (
	LOG_LEVELS   = []string{"DEBUG", "INFO", "WARN", "ERROR"}
	HTTP_METHODS = []string{"GET", "POST", "PUT", "DELETE"}

	HTTP_CODES_1xx = []int{100, 101, 102}
	HTTP_CODES_2xx = []int{200, 201, 202, 203, 204, 205, 206, 207, 208, 226}
	HTTP_CODES_3xx = []int{301, 302, 303, 304, 305, 306, 307, 308}
	HTTP_CODES_4xx = []int{400, 401, 402, 403, 404, 405, 406, 407, 408, 409, 410, 411, 412, 413, 414, 415, 416, 417, 418, 421, 422, 423, 424, 426, 428, 429, 431, 444, 451, 499}
	HTTP_CODES_5xx = []int{500, 501, 502, 503, 504, 505, 506, 507, 508, 510, 511, 599}

	DATE_FORMAT = time.RFC3339
	IP_FORMAT   = "%d.%d.%d.%d"

	// "131.136.113.113 - 2017-06-10T18:16:15+05:30 - ERROR - GET - HTTP/1.1 - 200 - 11382"
	LOG_LINE_FORMAT = "%s - %s - %s - %s - HTTP/1.1 - %d -%d"
)

func NewLogLine() string {
	newLog := generateLog()
	return fmt.Sprintf(
		LOG_LINE_FORMAT,
		newLog.IP,
		newLog.DateTime,
		newLog.LogLevel,
		newLog.HttpMethod,
		newLog.HttpResponseCode,
		newLog.RequestTimeTaken,
	)
}

func generateLog() *Log {
	return &Log{
		IP:               getIP(),
		DateTime:         getDateTime(),
		LogLevel:         getLogLevel(),
		HttpMethod:       getHttpMethod(),
		HttpResponseCode: getHttpResponseCode(),
		RequestTimeTaken: getRequestTimeTaken(),
	}
}

func getIP() string {
	return fmt.Sprintf(IP_FORMAT, rand.Intn(255), rand.Intn(255), rand.Intn(255), rand.Intn(255))
}

func getDateTime() string {
	return time.Now().Format(DATE_FORMAT)
}

func getLogLevel() string {
	return LOG_LEVELS[rand.Intn(len(LOG_LEVELS))]
}

func getHttpMethod() string {
	return HTTP_METHODS[rand.Intn(len(HTTP_METHODS))]
}

func getHttpResponseCode() int {
	codes := getCodes()
	return codes[rand.Intn(len(codes))]
}

func getCodes() []int {
	responseCodes := append(HTTP_CODES_1xx, HTTP_CODES_2xx...)
	responseCodes = append(responseCodes, HTTP_CODES_3xx...)
	responseCodes = append(responseCodes, HTTP_CODES_4xx...)
	return append(responseCodes, HTTP_CODES_5xx...)
}

func getRequestTimeTaken() int {
	return (rand.Intn(200) + 60)
}
