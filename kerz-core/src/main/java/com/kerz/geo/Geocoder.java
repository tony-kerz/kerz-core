package com.kerz.geo;

import com.kerz.geo.domain.GeoPoint;

public interface Geocoder
{
  public GeocoderResponse geocode(String address);
  public GeocoderResponse reverse(GeoPoint geoPoint);
}
