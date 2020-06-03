package com.imengyu.datacenter.entity;

import com.imengyu.datacenter.utils.Result;

public class CheckPermissionResult {

  private Result failResult;
  private boolean success;

  public Result getFailResult() {
    return failResult;
  }
  public void setFailResult(Result failResult) {
    this.failResult = failResult;
  }

  public boolean getSuccess() {
    return success;
  }
  public void setSuccess(boolean success) {
    this.success = success;
  }

  public CheckPermissionResult(Result failResult) {
    this.failResult = failResult;
    success = false;
  }
  public CheckPermissionResult() {
    success = true;
  }
}
