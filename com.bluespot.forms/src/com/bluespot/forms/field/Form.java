package com.bluespot.forms.field;

import com.bluespot.forms.commit.Committable;
import com.bluespot.forms.commit.CommittableGroup;
import com.bluespot.forms.property.DefaultPropertySheet;

public class Form extends Field<DefaultPropertySheet> {

    public Form(final Description description) {
        super(new CommittableGroup(), description);
    }

    public void addField(final Field<?> field) {
        this.getCommittableGroup().addCommittable(field.getDescription().getId(), field.getCommittable());
    }

    public void addField(final String name, final Committable<?> committable) {
        this.getCommittableGroup().addCommittable(name, committable);
    }

    public void addField(final String name, final Field<?> field) {
        this.addField(name, field.getCommittable());
    }

    public void clearFields() {
        this.getCommittableGroup().clearCommittables();
    }

    public CommittableGroup getCommittableGroup() {
        return (CommittableGroup) this.getCommittable();
    }

    public void removeField(final Field<?> field) {
        this.getCommittableGroup().removeCommittable(field.getDescription().getId(), field.getCommittable());
    }

    public void removeField(final String name, final Committable<?> committable) {
        this.getCommittableGroup().removeCommittable(name, committable);
    }
}