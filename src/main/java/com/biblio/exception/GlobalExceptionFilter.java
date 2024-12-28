package com.biblio.exception;

import com.biblio.dto.response.ApiResponse;
import com.biblio.utils.ApiResponseWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GlobalExceptionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        try {
            chain.doFilter(request, response);
        } catch (AppException appException) {
            handleAppException(httpServletResponse, appException);
        } catch (IllegalArgumentException e) {
            handleValidationException(httpServletResponse, e);
        } catch (RuntimeException runtimeException) {
            handleRuntimeException(httpServletResponse, runtimeException);
        }
    }

    private void handleRuntimeException(HttpServletResponse response, RuntimeException exception) throws IOException {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());
        ApiResponseWriter.writeResponse(response, apiResponse);
    }

    private void handleAppException(HttpServletResponse response, AppException exception) throws IOException {
        ApiResponse apiResponse = new ApiResponse();
        ErrorCode errorCode = exception.getErrorCode();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        ApiResponseWriter.writeResponse(response, apiResponse);
    }

    private void handleValidationException(HttpServletResponse response, IllegalArgumentException exception) throws IOException {
        ApiResponse apiResponse = new ApiResponse();
        ErrorCode errorCode = ErrorCode.INVALID_KEY;
        try {
            errorCode = ErrorCode.valueOf(exception.getMessage());
        } catch (IllegalArgumentException e) {
            // Leave errorCode as INVALID_KEY if no match is found
        }
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        ApiResponseWriter.writeResponse(response, apiResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) {
        // Initialization code, if needed
    }

    @Override
    public void destroy() {
        // Cleanup code, if needed
    }
}
