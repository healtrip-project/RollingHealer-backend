package com.ssafy.rollinghealer.place.model.service;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.rollinghealer.place.model.AreaBasedList1ApiQueryParams;
import com.ssafy.rollinghealer.place.model.PlaceInfoDto;
import com.ssafy.rollinghealer.place.model.mapper.PlaceMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Service
@RequiredArgsConstructor
@Slf4j
public class PlaceServiceImpl implements PlaceService {
	private final RestTemplate restTemplate;
	private final PlaceMapper placeMapper;
	@Value("${data.api.key}")
	private String serviceKey;

	@Override
	@Transactional
	@Async("threadPoolTaskExecutor")
	public void fetchPlaceInfoDataByAreaCode(AreaBasedList1ApiQueryParams params) throws Exception {
		// TODO Auto-generated method stub
		if(params.getNumOfRows()!=0) {
			UriComponents uriComponent= UriComponentsBuilder.fromHttpUrl("https://apis.data.go.kr/B551011/KorService1/{areaBasedList1}")
					.queryParam("serviceKey", "{serviceKey}").encode()
					.queryParam("pageNo", params.getPageNo())
					.queryParam("numOfRows",params.getNumOfRows())
					.queryParam("areaCode",params.getAreaCode())
					.queryParam("MobileOS", params.getMobileOS())
					.queryParam("MobileApp", params.getMobileApp())
					.queryParam("_type",params.get_type())
					.queryParam("contentTypeId", params.getContentTypeId())
					.queryParam("listYN", params.getListYN())
					.queryParam("arrange",params.getArrange())
					.encode()
					.buildAndExpand("areaBasedList1",serviceKey);
					
			HttpHeaders headers = new HttpHeaders();
			headers.set("Content-Type", "application/json");
			HttpEntity<String> entity=new HttpEntity<>(headers);
			
			
//			URL url = new URL(uriComponent.toUriString());
//			InputStream in = url.openStream();
//			BufferedReader br = new BufferedReader(new InputStreamReader(in));
//			while (true) {
//				String line = br.readLine();
//				if (line == null) break;
// 				System.out.println(line);
//			}
			
			ObjectMapper mapper = new ObjectMapper();
//			mapper.readTree(in).

//		    TypeReference<HashMap<String,Object>> typeRef = new TypeReference<HashMap<String,Object>>() {};
//		    HashMap<String,Object> o = mapper.readValue(in, typeRef); 
//			System.out.println(o);
			ResponseEntity<?> response  = restTemplate.exchange(uriComponent.toUri(),HttpMethod.GET,entity, String.class);

			JsonNode result=mapper.readTree(response.getBody().toString());
			List<PlaceInfoDto> placeInfoDtoList = mapper.readValue(result.get("response").get("body").get("items").get("item").toString(),new TypeReference<List<PlaceInfoDto>>() {});
//			System.out.println(result);
			placeMapper.insertPlaceInfoDtoList(placeInfoDtoList);
			
		}
	}

}
