<template>
  <VRow>
    <VCol
      cols="12"
      sm="12"
      md="12"
    >
      <VExpansionPanels variant="accordion">
        <VExpansionPanel>
          <VExpansionPanelTitle> Ajouter un Département </VExpansionPanelTitle>
          <VExpansionPanelText>
            <VForm
              ref="addDep"
              @submit.prevent="addDep"
              class="mt-1 mb-1"
            >
              <VRow>
                <VCol
                  cols="12"
                  md="10"
                >
                  <VTextField
                    v-model="departement.nom"
                    placeholder="ex : ......"
                    label="Département"
                    density="compact"
                    :rules="[rules.required]"
                    clearable
                    persistent-placeholder
                  />
                </VCol>

                <VCol
                  cols="12"
                  md="2"
                >
                  <VBtn
                    type="submit"
                    block
                  >
                    Enregistrer
                  </VBtn>
                </VCol>
              </VRow>
            </VForm>
          </VExpansionPanelText>
        </VExpansionPanel>

        <VExpansionPanel>
          <VExpansionPanelTitle> Liste des Départements </VExpansionPanelTitle>
          <VExpansionPanelText>
            <VTextField
              v-model="search"
              placeholder="Recherchez quelque chose ......"
              label="Search"
              density="compact"
              clearable
              persistent-placeholder
            />
            <VDataTable
              :headers="headers"
              :items="departementList"
              :search="search"
              :items-per-page="5"
              density="compact"
            >
              <template #item.actions="{ item }">
                <VDialog
                  v-model="modifier"
                  persistent
                  class="v-dialog-sm"
                >
                  <!-- Dialog Activator -->
                  <template #activator="{ props }">
                    <VBtn
                      variant="text"
                      @click="open(item.raw)"
                      v-bind="props"
                    >
                      <VIcon icon="mdi-backup-outline"></VIcon>
                      <VTooltip
                        loDepion="top"
                        activator="parent"
                      >
                        Modifier la Département {{ item.raw.nom }}
                      </VTooltip>
                    </VBtn>
                  </template>

                  <!-- Dialog Content -->
                  <VCard title="Modifier une Département">
                    <VForm
                      ref="updateDep"
                      @submit.prevent="updateDep"
                      class="mt-1 mb-1"
                    >
                      <DialogCloseBtn
                        variant="text"
                        size="small"
                        @click="modifier = false"
                      />

                      <VCardText>
                        <VRow>
                          <VCol
                            cols="12"
                            md="12"
                          >
                            <VTextField
                              v-model="nom"
                              placeholder="ex : ......"
                              label="Département"
                              density="compact"
                              :rules="[rules.required]"
                              clearable
                              persistent-placeholder
                            />
                          </VCol>
                        </VRow>
                      </VCardText>

                      <VCardActions>
                        <VSpacer />
                        <VBtn
                          color="error"
                          variant="outlined"
                          @click="modifier = false"
                        >
                          Annuler
                        </VBtn>
                        <VBtn
                          color="primary"
                          variant="outlined"
                          type="submit"
                        >
                          Modifier
                        </VBtn>
                      </VCardActions>
                    </VForm>
                  </VCard>
                </VDialog>
              </template>
            </VDataTable>
          </VExpansionPanelText>
        </VExpansionPanel>
      </VExpansionPanels>
    </VCol>
  </VRow>
</template>

<script>
import axios from 'axios'
import Swal from 'sweetalert2'
import { VDataTable } from 'vuetify/labs/VDataTable'
import { useToast } from 'vue-toast-notification'
import 'vue-toast-notification/dist/theme-sugar.css'

export default {
  components: {
    VDataTable,
  },

  setup() {
    const search = ref('')
    const toast = useToast()
    const ajouter = ref(false)
    const modifier = ref(false)
    const headers = [
      { title: 'ID', sortable: false, key: 'id' },
      { title: 'NOMS', key: 'nom' },
      { title: 'ACTIONS', sortable: false, key: 'actions' },
    ]

    return {
      toast,
      search,
      ajouter,
      headers,
      modifier,
    }
  },

  data() {
    return {
      search: '',
      departementList: [],
      id: '',
      nom: '',
      student: '',
      departement: {
        nom: '',
        statut: '',
      },

      rules: {
        required: value => !!value || 'Désolé mais ce champ est obligatoire.',
      },
    }
  },

  created() {
    this.getDatas()

    let account = JSON.parse(localStorage.getItem('account'))
    console.log(account)
    if (!account) {
      Swal.fire({
        icon: 'warning',
        text: 'Attention, cette connexion est illégitime.',
        toast: true,
        position: 'top-end',
        showCancelButton: false,
        showConfirmButton: false,
        timer: 5000,
        timerProgressBar: true,
      })
      this.$router.push('/login')
    } else {
      if (account.created_by != 'etudiant') {
        this.student = false
      }
    }
  },

  methods: {
    async getDatas() {
      try {
        axios
          .get('allDep')
          .then(response => {
            this.departementList = response.data
          })
          .catch(error => {
            console.log(error)
          })
      } catch (error) {
        console.log(error)
      }
    },

    async addDep() {
      try {
        const valid = this.$refs.addDep.validate()
        console.log(valid)
        if (valid) {
          Swal.fire({
            title: 'Êtes vous sûres ?',
            html: 'Souhaitez vous enregistrer la Département <b>' + this.departement.nom + '<b> ?',
            showDenyButton: true,
            showCancelButton: false,
            confirmButtonText: 'Je le souhaite',
            denyButtonText: `Annuler`,
          }).then(result => {
            if (result.isConfirmed) {
              axios
                .post('addDep', {
                  nom: this.departement.nom,
                })
                .then(response => {
                  this.toast.info(response.data, {
                    position: 'top-right',
                    duration: 7500,
                  })
                  this.departement.nom = ''
                  this.getDatas()
                })
            }
          })
        } else {
          this.toast.error('Données abscentes ou incorrectes', { position: 'top-right', duration: 7500 })
        }
      } catch (error) {
        console.log(error)
      }
    },

    async open(data) {
      this.modifier = true
      this.id = data.id
      this.nom = data.nom
    },

    async updateDep() {
      try {
        const valid = this.$refs.updateDep.validate()
        console.log(valid)
        if (valid) {
          this.modifier = false
          Swal.fire({
            title: 'Êtes vous sûres ?',
            html: 'Souhaitez vous modifier ce Département  en : <b>' + this.departement.nom + '<b> ?',
            showDenyButton: true,
            showCancelButton: false,
            confirmButtonText: 'Je le souhaite',
            denyButtonText: `Annuler`,
          }).then(result => {
            if (result.isConfirmed) {
              axios
                .put('updateDep/' + this.id, {
                  nom: this.nom,
                })
                .then(response => {
                  this.toast.info(response.data, {
                    position: 'top-right',
                    duration: 7500,
                  })
                  this.modifier = false
                  this.getDatas()
                })
            }
          })
        } else {
          this.toast.error('Données abscentes ou incorrectes', { position: 'top-right', duration: 7500 })
        }
      } catch (error) {
        console.log(error)
      }
    },
  },
}
</script>
