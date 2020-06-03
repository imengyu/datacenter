package com.imengyu.datacenter.utils.request;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PageRequestUtils {

  /**
   * 从 URL 自动创建分页和排序参数
   * @param pageIndex 页
   * @param pageSize 页大小
   * @param request 请求
   */
  public static PageRequest createPageRequestAndSort(int pageIndex, int pageSize, HttpServletRequest request) {
    return PageRequest.of(pageIndex, pageSize, createSortByUrlParams(request));
  }

  /**
   * 从 URL 自动创建排序参数
   * @param request 请求
   */
  public static Sort createSortByUrlParams(HttpServletRequest request) {
    List<Sort.Order> sortOrders = new ArrayList<>();
    Map<String, String[]> paramaMap = request.getParameterMap();
    for(Map.Entry<String, String[]> entry : paramaMap.entrySet()) {
      if("sort".equals(entry.getKey())) {
        String[] keys = entry.getValue();
        for (String key : keys) {
          String sortKey = key;
          Sort.Direction direction = Sort.Direction.ASC;
          if (key.contains(":")){
            String[] sortKeySp = key.split(":");
            if(sortKeySp.length == 2) {
              sortKey = sortKeySp[0];
              direction = "asc".equals(sortKeySp[1]) ? Sort.Direction.ASC : Sort.Direction.DESC;
            }
          }
          sortOrders.add(new Sort.Order(direction, sortKey));
        }
      }
      break;
    }
    return Sort.by(sortOrders);
  }

}
