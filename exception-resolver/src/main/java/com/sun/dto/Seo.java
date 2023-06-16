package com.sun.dto;

import com.sun.vo.SeoTagVo;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class Seo {
    private List<SeoTagVo> ogp;
    private Map<String, Object> jsonLd;
}
