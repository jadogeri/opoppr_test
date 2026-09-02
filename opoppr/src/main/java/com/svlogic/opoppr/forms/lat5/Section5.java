package com.svlogic.opoppr.forms.lat5;

import java.io.Serializable;
import java.util.List;

import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Named;

import com.svlogic.opoppr.model.NoaPpLat5Filing;

/**
 *
 * @author David
 */
@Named("lat5Section5")
@ConversationScoped
public class Section5 extends FilingForm implements Serializable {
    public Section5() {
        super(5, "99");
    }

    @Override
    protected List<NoaPpLat5Filing> getAddUpdateFilings() {
        return getFilings()
                .stream()
                .filter(f -> f.getNoaPpLat5Filing().getItemDescription() != null
                        && !f.getNoaPpLat5Filing().getItemDescription().isEmpty())
                .map(Filing::getNoaPpLat5Filing)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    protected List<NoaPpLat5Filing> getDeleteFilings() {
        return getFilings()
                .stream()
                .filter(f -> (f.getNoaPpLat5Filing().getItemDescription() == null
                        || f.getNoaPpLat5Filing().getItemDescription().isEmpty())
                        && f.getNoaPpLat5Filing().getNoaPpLat5FilingId() != null)
                .map(Filing::getNoaPpLat5Filing)
                .collect(java.util.stream.Collectors.toList());
    }
}
