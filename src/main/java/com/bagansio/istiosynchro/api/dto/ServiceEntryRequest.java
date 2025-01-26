package com.bagansio.istiosynchro.api.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * ServiceEntryRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-01-26T22:29:08.193806304+01:00[Europe/Paris]")
public class ServiceEntryRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  private String name;

  private String host;

  private Integer port;

  private String protocol;

  public ServiceEntryRequest name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
  */
  
  @Schema(name = "name", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ServiceEntryRequest host(String host) {
    this.host = host;
    return this;
  }

  /**
   * Get host
   * @return host
  */
  
  @Schema(name = "host", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("host")
  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public ServiceEntryRequest port(Integer port) {
    this.port = port;
    return this;
  }

  /**
   * Get port
   * @return port
  */
  
  @Schema(name = "port", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("port")
  public Integer getPort() {
    return port;
  }

  public void setPort(Integer port) {
    this.port = port;
  }

  public ServiceEntryRequest protocol(String protocol) {
    this.protocol = protocol;
    return this;
  }

  /**
   * Get protocol
   * @return protocol
  */
  
  @Schema(name = "protocol", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("protocol")
  public String getProtocol() {
    return protocol;
  }

  public void setProtocol(String protocol) {
    this.protocol = protocol;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ServiceEntryRequest serviceEntryRequest = (ServiceEntryRequest) o;
    return Objects.equals(this.name, serviceEntryRequest.name) &&
        Objects.equals(this.host, serviceEntryRequest.host) &&
        Objects.equals(this.port, serviceEntryRequest.port) &&
        Objects.equals(this.protocol, serviceEntryRequest.protocol);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, host, port, protocol);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ServiceEntryRequest {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    host: ").append(toIndentedString(host)).append("\n");
    sb.append("    port: ").append(toIndentedString(port)).append("\n");
    sb.append("    protocol: ").append(toIndentedString(protocol)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

