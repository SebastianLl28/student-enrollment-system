package pe.utp.marcodesarrolloweb.configuration.common;

import java.util.ArrayList;
import java.util.List;

public record OrderResponse(
    String id, String status, List<Link> links
) {

}
